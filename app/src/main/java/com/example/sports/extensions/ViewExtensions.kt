package com.example.sports.extensions

import android.os.SystemClock
import android.view.View

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

var lastClickTime = 0L

/**
 * extension function in order to avoid multiple clicks
 */
fun View.setOnSingleClickListener(elapsedTime: Long = 2000, block: () -> Unit) {
    setOnClickListener(View.OnClickListener {
        if (SystemClock.elapsedRealtime() - lastClickTime < elapsedTime) {
            return@OnClickListener
        }
        lastClickTime = SystemClock.elapsedRealtime()

        block()
    })
}