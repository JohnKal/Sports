package com.example.sports.utils

import android.content.Context
import android.os.CountDownTimer
import android.util.AttributeSet
import com.example.sports.extensions.False
import com.example.sports.extensions.True

class TimeCounterView : androidx.appcompat.widget.AppCompatTextView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    var countDownTimer: CountDownTimer? = null

    fun startCountDown(expireTimeStamp: Long, removeItem: () -> Unit = {}) {
        stopCounter()
        calculateTimeExpire(expireTimeStamp, removeItem)
    }

    private fun calculateTimeExpire(expireTimeStamp: Long, removeItem: () -> Unit) {
        countDownTimer = object : CountDownTimer(
            expireTimeStamp * 1000 - System.currentTimeMillis(),
            ONE_MILLI_SEC
        ) {
            override fun onTick(millisUntilFinished: Long) {
                var seconds = millisUntilFinished / 1000 % 60
                var minutes = millisUntilFinished / (60 * 1000) % 60
                var hours = millisUntilFinished / (60 * 60 * 1000) % 24

                val displaySeconds = (seconds < 10) True "0$seconds" False "$seconds"
                val displayMinutes = (minutes < 10) True "0$minutes" False "$minutes"
                val displayHours = (hours < 10) True "0$hours" False "$hours"

                text = "${displayHours}:${displayMinutes}:${displaySeconds}"
            }

            override fun onFinish() {
                text = "00:00:00"
                removeItem()
            }
        }
        countDownTimer?.start()
    }

    fun stopCounter() {
        countDownTimer?.cancel()
    }

    companion object {
        const val ONE_MILLI_SEC: Long = 1000
    }
}