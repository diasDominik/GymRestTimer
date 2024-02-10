package de.dominikdias.gymresttimer.viewmodel

import android.os.CountDownTimer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import de.dominikdias.gymresttimer.data.ScreenNavigation

class MainActivityViewModel : ViewModel() {

    var timerText by mutableStateOf("")
        private set

    var currentRoute by mutableStateOf<ScreenNavigation>(ScreenNavigation.Home)

    private var timer: CountDownTimer? = null

    private var chosenTime: Long = 0

    fun startTimer(time: Long) {
        timer?.cancel()
        chosenTime = time
        timer = object : CountDownTimer(chosenTime, 1000) {
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
