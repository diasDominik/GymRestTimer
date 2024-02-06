package de.dominikdias.gymresttimer.viewmodel

import android.os.CountDownTimer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel() {

    private var timer: CountDownTimer? = null

    private var chosenTime: Long = 0

    var timerText by mutableStateOf("")
        private set

    fun startTimer(time: Long) {
        timer?.cancel()
        chosenTime = time
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