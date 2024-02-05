package de.dominikdias.gymresttimer.viewmodel

import android.os.CountDownTimer
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class MainActivityViewModel: ViewModel() {
    @Stable
    val timerList = listOf(60.seconds, 30.seconds, 20.seconds, 2.minutes, 3.minutes, 4.minutes, 5.minutes)

    private var timer: CountDownTimer? = null

    private var chosenTime: Long = 0

    var timerText by mutableStateOf("")
        private set

    fun startTimer(time: Duration) {
        timer?.cancel()
        chosenTime = time.inWholeMilliseconds
        timer = object: CountDownTimer(chosenTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timerText = (millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {
                timerText = "00"
                chosenTime = 0
            }

        }
        timer?.start()
    }

    fun stopTimer() {
        timer?.cancel()
        chosenTime = 0
    }

    fun resetTimer() {
        timer?.cancel()
        timerText = chosenTime.toString()
    }
}