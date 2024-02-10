package de.dominikdias.gymresttimer.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.dominikdias.database.viewmodel.DatabaseViewModel
import de.dominikdias.gymresttimer.R
import de.dominikdias.gymresttimer.application.GymRestTimerApplication
import de.dominikdias.gymresttimer.data.ScreenNavigation
import de.dominikdias.gymresttimer.data.Screens
import de.dominikdias.gymresttimer.viewmodel.MainActivityViewModel
import de.dominikdias.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    private val navigationRoute = listOf(
        ScreenNavigation.Home,
        ScreenNavigation.AddTimer,
        ScreenNavigation.Settings,
    )

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainActivityViewModel by viewModels<MainActivityViewModel>()
        val databaseViewModel by viewModels<DatabaseViewModel> {
            DatabaseViewModel.factory(
                (application as GymRestTimerApplication).durationRepository
            )
        }
        setContent {
            MyApplicationTheme {
                val durationList by databaseViewModel.durationList.collectAsState()
                val navController = rememberNavController()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
/*                    LazyColumn {
                        item {
                            Text(text = mainActivityViewModel.timerText)
                        }
                        items(durationList) {
                            Button(onClick = { mainActivityViewModel.startTimer(it.duration) }) {
                                Text(text = (it.duration / 1000).toString())
                            }
                        }
                    }*/
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
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = mainActivityViewModel.currentRoute.route.name,
                            modifier = Modifier.padding(it),
                        ) {
                            composable(route = Screens.HOME.name) {
                                Text(text = "HOME")
                            }
                            composable(route = Screens.ADD_TIMER.name) {
                                Text(text = "ADD_TIMER")
                            }
                            composable(route = Screens.SETTINGS.name) {
                                Text(text = "SETTINGS")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun MainActivityPreview() {
    MainActivity()
}
