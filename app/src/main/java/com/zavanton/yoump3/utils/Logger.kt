package com.zavanton.yoump3.utils

import android.util.Log
import java.util.regex.Pattern

object Logger {

    private const val CALL_STACK_INDEX = 2
    private val ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$")

    fun d(message: String) {
        Log.d("zavantondebug", prependCallLocation(message))
    }

    fun e(message: String, throwable: Throwable?) {
        Log.e("zavantondebug", prependCallLocation(message), throwable)
    }

    private fun prependCallLocation(message: String): String {
        val stackTrace = Throwable().stackTrace
        if (stackTrace.size <= CALL_STACK_INDEX) {
            throw IllegalStateException(
                "Synthetic stacktrace didn't have enough elements: are you using proguard?"
            )
        }
        val clazz = extractClassName(stackTrace[CALL_STACK_INDEX])
        val lineNumber = stackTrace[CALL_STACK_INDEX].lineNumber

        return "($clazz:$lineNumber): $message"
    }

    private fun extractClassName(element: StackTraceElement): String {
        var tag = element.fileName
        val m = ANONYMOUS_CLASS.matcher(tag)
        if (m.find()) {
            tag = m.replaceAll("")
        }
        return tag
    }
}