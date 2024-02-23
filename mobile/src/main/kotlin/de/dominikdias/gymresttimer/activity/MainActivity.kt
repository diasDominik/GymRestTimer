package de.dominikdias.gymresttimer.activity

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.VibrationEffect.DEFAULT_AMPLITUDE
import android.os.Vibrator
import android.os.VibratorManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.dominikdias.database.data.Duration
import de.dominikdias.database.viewmodel.DatabaseViewModel
import de.dominikdias.gymresttimer.R
import de.dominikdias.gymresttimer.application.GymRestTimerApplication
import de.dominikdias.gymresttimer.data.AppTheme
import de.dominikdias.gymresttimer.data.ScreenNavigation
import de.dominikdias.gymresttimer.data.Screens
import de.dominikdias.gymresttimer.ui.addtimer.AddTimer
import de.dominikdias.gymresttimer.ui.home.Home
import de.dominikdias.gymresttimer.ui.settings.Settings
import de.dominikdias.gymresttimer.viewmodel.AddTimerViewModel
import de.dominikdias.gymresttimer.viewmodel.MainActivityViewModel
import de.dominikdias.gymresttimer.viewmodel.SettingsViewModel
import de.dominikdias.gymresttimer.ui.theme.MyApplicationTheme
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {

    @Stable
    private val navigationRoute = listOf(
        ScreenNavigation.Home,
        ScreenNavigation.AddTimer,
        ScreenNavigation.Settings,
    )

    private val mainActivityViewModel: MainActivityViewModel by viewModels()
    private val databaseViewModel: DatabaseViewModel by viewModels {
        DatabaseViewModel.factory((application as GymRestTimerApplication).durationRepository)
    }
    private val addTimerViewModel: AddTimerViewModel by viewModels()
    private val settingsViewModel: SettingsViewModel by viewModels()
    private val vibrator by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            (getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager?)?.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            getSystemService(VIBRATOR_SERVICE) as Vibrator?
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val useDarkColors = when (settingsViewModel.useSystemTheme) {
                AppTheme.MODE_DAY -> false
                AppTheme.MODE_NIGHT -> true
                AppTheme.MODE_AUTO -> isSystemInDarkTheme()
            }
            MyApplicationTheme(darkTheme = useDarkColors) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppContent()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun AppContent() {
        val navController = rememberNavController()
        val durationList by databaseViewModel.durationList.collectAsState()
        LaunchedEffect(key1 = Unit) {
            mainActivityViewModel.eventChannel.collectLatest {
                withContext(Dispatchers.Main) {
                    if (vibrator?.hasVibrator() == true) {
                        vibrator?.vibrate(VibrationEffect.createOneShot(500, DEFAULT_AMPLITUDE))
                    }
                }
            }
        }
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(text = stringResource(id = R.string.app_name)) },
                    navigationIcon = {
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                contentDescription = null,
                            )
                        }
                    })
            },
            bottomBar = {
                BottomNavigationBar(navController)
            },
            floatingActionButton = {
                if (mainActivityViewModel.currentRoute.route != ScreenNavigation.AddTimer.route) {
                    FloatingActionButton(
                        onClick = {
                            mainActivityViewModel.currentRoute = ScreenNavigation.AddTimer
                            navController.navigate(ScreenNavigation.AddTimer.route.name)
                        }
                    ) {
                        Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
                    }
                }
            },
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = mainActivityViewModel.currentRoute.route.name,
                modifier = Modifier.padding(paddingValues),
            ) {
                composable(route = Screens.HOME.name) {
                    Home(
                        time = mainActivityViewModel.timerText,
                        times = durationList.toImmutableList(),
                        onPlay = mainActivityViewModel::startTimer,
                        onPause = mainActivityViewModel::pauseTimer,
                        onStop = mainActivityViewModel::stopTimer,
                        onTimeItemClicked = mainActivityViewModel::setTimer
                    )
                }
                composable(route = Screens.ADD_TIMER.name) {
                    AddTimer(
                        hourValue = addTimerViewModel.hours,
                        minuteValue = addTimerViewModel.minutes,
                        secondValue = addTimerViewModel.seconds,
                        onHourValueChanged = { addTimerViewModel.hours = it },
                        onMinutesValueChanged = { addTimerViewModel.minutes = it },
                        onSecondsValueChanged = { addTimerViewModel.seconds = it }
                    ) { timeToAdd ->
                        if (timeToAdd != 0L) {
                            Duration(timeToAdd).also { duration ->
                                val found = durationList.find { it == duration }
                                if (found == null) {
                                    Toast.makeText(this@MainActivity, "Timer Added", Toast.LENGTH_SHORT).show()
                                    databaseViewModel.insertDuration(duration)
                                } else {
                                    Toast.makeText(
                                        this@MainActivity, "Timer already added before", Toast
                                            .LENGTH_SHORT
                                    ).show()
                                }
                            }
                            addTimerViewModel.reset()

                        }
                    }
                }
                composable(route = Screens.SETTINGS.name) {
                    Settings(selected = settingsViewModel.useSystemTheme.name) {
                        settingsViewModel.useSystemTheme = AppTheme.valueOf(it)
                    }
                }
            }
        }
    }

    @Composable
    private fun BottomNavigationBar(navController: NavHostController) {
        NavigationBar {
            navigationRoute.forEach {
                NavigationBarItem(
                    selected = it.route.name == mainActivityViewModel.currentRoute.route.name,
                    onClick = {
                        mainActivityViewModel.currentRoute = it
                        navController.navigate(mainActivityViewModel.currentRoute.route.name) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true

                            restoreState = true
                        }
                    },
                    icon = { Icon(imageVector = it.icon, contentDescription = null) },
                    label = { Text(text = stringResource(id = it.name)) },
                )
            }
        }
    }
}

@Preview
@Composable
private fun MainActivityPreview() {
    MainActivity()
}
