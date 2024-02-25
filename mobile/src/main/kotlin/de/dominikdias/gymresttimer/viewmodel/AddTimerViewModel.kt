package de.dominikdias.gymresttimer.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AddTimerViewModel: ViewModel() {
    var hours by mutableStateOf("")
    var minutes by mutableStateOf("")
    var seconds by mutableStateOf("")

    fun reset() {
        hours = ""
        minutes = ""
        seconds = ""
    }
}