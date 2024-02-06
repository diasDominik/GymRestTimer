package de.dominikdias.gymresttimer.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.dominikdias.database.viewmodel.DatabaseViewModel
import de.dominikdias.gymresttimer.application.GymRestTimerApplication
import de.dominikdias.gymresttimer.viewmodel.MainActivityViewModel
import de.dominikdias.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainActivityViewModel by viewModels<MainActivityViewModel>()
        val databaseViewModel by viewModels<DatabaseViewModel> { DatabaseViewModel.factory(
            (application as GymRestTimerApplication).durationRepository) }
        setContent {
            MyApplicationTheme {
                val durationList by databaseViewModel.durationList.collectAsState()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LazyColumn {
                        item {
                            Text(text = mainActivityViewModel.timerText)
                        }
                        items(durationList) {
                            Button(onClick = { mainActivityViewModel.startTimer(it.duration) }) {
                                Text(text = (it.duration / 1000).toString())
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}