package com.zavanton.yoump3.utils

import android.util.Log

object Logger {

    fun d(message: String) {
        Log.d("zavantondebug", message)
    }

    fun e(message: String, throwable: Throwable?) {
        Log.e("zavantondebug", message, throwable)
    }
}