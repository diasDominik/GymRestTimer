package de.dominikdias.gymresttimer.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import de.dominikdias.gymresttimer.data.AppTheme

class SettingsViewModel: ViewModel() {
    var useSystemTheme by mutableStateOf(AppTheme.MODE_AUTO)
}