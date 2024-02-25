package de.dominikdias.gymresttimer.ui.addtimer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import de.dominikdias.gymresttimer.ui.theme.MyApplicationTheme
import kotlin.time.DurationUnit
import kotlin.time.DurationUnit.HOURS
import kotlin.time.DurationUnit.MINUTES
import kotlin.time.DurationUnit.SECONDS
import kotlin.time.toDuration

@Composable
fun AddTimer(
    hourValue: String,
    minuteValue: String,
    secondValue: String,
    onHourValueChanged: (String) -> Unit,
    onMinutesValueChanged: (String) -> Unit,
    onSecondsValueChanged: (String) -> Unit,
    onAddTimer: (Long) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = hourValue,
            onValueChange = { if (it.isDigitsOnly()) onHourValueChanged(it) },
            label = { Text(text = "Hours") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(4.dp))
        TextField(
            value = minuteValue,
            onValueChange = { if (it.isDigitsOnly()) onMinutesValueChanged(it) },
            label = { Text(text = "Minutes") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(4.dp))
        TextField(
            value = secondValue,
            onValueChange = { if (it.isDigitsOnly()) onSecondsValueChanged(it) },
            label = { Text(text = "Seconds") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                onAddTimer(
                    hourValue.toMilliseconds(HOURS) +
                            minuteValue.toMilliseconds(MINUTES) +
                            secondValue.toMilliseconds(SECONDS)
                )
                keyboardController?.hide()
            },
        ) {
            Text(text = "Add Timer")
        }
    }
}

private fun String.toMilliseconds(unit: DurationUnit): Long {
    return if (this.isNotBlank()) this.toLong().toDuration(unit).inWholeMilliseconds else 0L
}

@Preview
@Composable
private fun AddTimerPreview() {
    MyApplicationTheme {
        var valueHours by remember { mutableStateOf("") }
        var valueMinutes by remember { mutableStateOf("") }
        var valueSeconds by remember { mutableStateOf("") }

        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            AddTimer(
                hourValue = valueHours,
                minuteValue = valueMinutes,
                secondValue = valueSeconds,
                onHourValueChanged = { valueHours = it },
                onMinutesValueChanged = { valueMinutes = it },
                onSecondsValueChanged = { valueSeconds = it },
                onAddTimer = {}
            )
        }
    }
}