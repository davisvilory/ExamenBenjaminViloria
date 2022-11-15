package com.mobile.examenbenjaminviloria.utils

interface ExceptionListener {
    fun uncaughtException(thread: Thread, throwable: Throwable)
}