package de.dominikdias.gymresttimer.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import de.dominikdias.gymresttimer.data.AppTheme
import de.dominikdias.gymresttimer.ui.theme.MyApplicationTheme

@Composable
fun Settings(
    selected: String,
    onClick: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Row {
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Theme",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            HorizontalDivider()
        }
        items(AppTheme.entries) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { onClick(it.name) }
            ) {
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = it.displayName)
                Spacer(modifier = Modifier.weight(1f))
                RadioButton(
                    selected = it.name == selected,
                    onClick = { onClick(it.name) },
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun SettingsPreview() {
    var selected by remember { mutableStateOf(AppTheme.MODE_AUTO.name) }
    MyApplicationTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            Settings(
                selected = selected,
                onClick = { selected = it }
            )
        }
    }
}