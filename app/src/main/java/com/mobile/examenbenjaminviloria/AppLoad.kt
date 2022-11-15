package com.mobile.examenbenjaminviloria

import android.app.Application
import android.os.Handler
import android.os.Looper
import com.microsoft.appcenter.AppCenter
import com.mobile.examenbenjaminviloria.utils.ExceptionListener
import com.mobile.examenbenjaminviloria.utils.Global

class AppLoad : Application(), ExceptionListener {
    override fun onCreate() {
        super.onCreate()
        setupExceptionHandler()
        AppCenter.configure(this, "dcacc93f-e0a7-40ff-ad1f-a4473fec3ee8")
    }

    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        Global.logError("Log_AppLoad", throwable.message ?: "")
    }

    private fun setupExceptionHandler() {
        Handler(Looper.getMainLooper()).post {
            while (true) {
                try {
                    Looper.loop()
                } catch (e: Throwable) {
                    uncaughtException(Looper.getMainLooper().thread, e)
                }
            }
        }
        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            uncaughtException(t, e)
        }
    }
}