package com.example.sports.extensions

import android.util.Log
import com.example.sports.BuildConfig

fun Any.log(message: () -> String) {
    if (BuildConfig.DEBUG) Log.i(this::class.java.simpleName, message())
}