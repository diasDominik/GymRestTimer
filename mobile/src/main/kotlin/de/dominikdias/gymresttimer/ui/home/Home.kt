package de.dominikdias.gymresttimer.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import de.dominikdias.database.data.Duration
import de.dominikdias.extensions.Pause
import de.dominikdias.extensions.Stop
import de.dominikdias.extensions.formatTime
import de.dominikdias.myapplication.ui.theme.MyApplicationTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun Home(
    time: Long,
    times: ImmutableList<Duration>,
    onPlay: () -> Unit,
    onPause: () -> Unit,
    onStop: () -> Unit,
    onTimeItemClicked: (Long) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TimerText(time = time)
        Spacer(modifier = Modifier.height(16.dp))
        ControlButtons(onPlay = onPlay, onPause = onPause, onStop = onStop)
        Spacer(modifier = Modifier.height(16.dp))
        TimerButtonGrid(times = times, onTimeItemClicked = onTimeItemClicked)
    }
}

@Composable
private fun TimerText(time: Long) {
    Text(
        text = time.formatTime(),
        style = MaterialTheme.typography.headlineLarge
    )
}

@Composable
private fun ControlButtons(
    onPlay: () -> Unit,
    onPause: () -> Unit,
    onStop: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = onPlay) {
            Icon(imageVector = Icons.Rounded.PlayArrow, contentDescription = null)
        }
        IconButton(onClick = onPause) {
            Icon(imageVector = Icons.Rounded.Pause, contentDescription = null)
        }
        IconButton(onClick = onStop) {
            Icon(imageVector = Icons.Rounded.Stop, contentDescription = null)
        }
    }
}

@Composable
private fun TimerButtonGrid(
    times: ImmutableList<Duration>,
    onTimeItemClicked: (Long) -> Unit,
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxWidth(),
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(times, key = { it.duration }) {
            Button(
                modifier = Modifier.defaultMinSize(minHeight = 48.dp),
                shape = CircleShape,
                onClick = { onTimeItemClicked(it.duration) }
            ) {
                Text(text = it.duration.formatTime())
            }
        }
    }
}

@Composable
@PreviewLightDark
private fun PreviewHome() {
    val times = persistentListOf(
        Duration(60_000L),
        Duration(30_000L),
        Duration(10_000L),
        Duration(20_000L),
        Duration(40_000L),
    )
    var time by remember { mutableLongStateOf(0L) }

    MyApplicationTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Home(
                time = time,
                times = times,
                onPlay = {},
                onPause = {},
                onStop = {},
                onTimeItemClicked = { time = it }
            )
        }
    }
}
