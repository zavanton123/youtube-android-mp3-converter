package com.zavanton.yoump3.utils

import android.util.Log
import java.util.regex.Pattern

object Log {

    private const val PREFIX = "zavdeb"
    private const val CALL_STACK_INDEX = 2
    private val ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$")

    fun d() {
        Log.d(PREFIX, prependCallLocation(""))
    }

    fun e(throwable: Throwable?) {
        Log.e(PREFIX, prependCallLocation(""), throwable)
    }

    fun d(message: String) {
        Log.d(PREFIX, prependCallLocation(message))
    }

    fun e(throwable: Throwable?, message: String) {
        Log.e(PREFIX, prependCallLocation(message), throwable)
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
        val methodName = stackTrace[CALL_STACK_INDEX].methodName

        val processedMessage = if (message.isNotEmpty()) ": $message" else ""

        return "($clazz:$lineNumber) -> $methodName()$processedMessage"
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