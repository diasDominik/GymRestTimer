package de.dominikdias.gymresttimer.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.dominikdias.gymresttimer.data.ScreenNavigation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityViewModel : ViewModel() {

    var timerText by mutableLongStateOf(0L)
        private set

    var currentRoute by mutableStateOf<ScreenNavigation>(ScreenNavigation.Home)

    private var timerJob: Job? = null
    private var remainingTime: Long = 0

    private val _eventChannel = Channel<Unit>()
    val eventChannel: Flow<Unit>
        get() = _eventChannel.receiveAsFlow()

    fun setTimer(time: Long) {
        remainingTime = time
        timerText = time
    }

    fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch(Dispatchers.IO) {
            while (remainingTime > 0) {
                delay(1000)
                remainingTime -= 1000
                withContext(Dispatchers.Main) {
                    timerText = remainingTime
                }
            }
            _eventChannel.send(Unit)
        }
    }

    fun pauseTimer() {
        timerJob?.cancel()
    }

    fun stopTimer() {
        timerJob?.cancel()
        remainingTime = 0
        timerText = 0
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
        _eventChannel.close()
    }
}
