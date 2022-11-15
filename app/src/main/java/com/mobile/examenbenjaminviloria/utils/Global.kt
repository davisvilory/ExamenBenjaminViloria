package com.mobile.examenbenjaminviloria.utils

import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import java.text.SimpleDateFormat
import java.util.*

class Global {
    companion object {
        val events: MutableMap<String, String> = HashMap()
        var originalFragment: Int = 0
        const val thisTag = "Log_Global"

        fun initAnalytics() {
            if (AppCenter.isConfigured()) {
                AppCenter.start(Analytics::class.java)
                AppCenter.start(Crashes::class.java)
            }
        }

        fun showProgress(show: Boolean, win: Window, loader: FrameLayout) {
            if (show) {
                win.setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                )
                loader.visibility = View.VISIBLE
            } else {
                win.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                loader.visibility = View.GONE
            }
        }

        fun trackEvent(tag: String) {
            try {
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.US)
                val cd = sdf.format(Date())
                Analytics.trackEvent("${tag}_${cd}", events)
            } catch (ex: Exception) {
                logDebug(thisTag, ex.toString())
            }
        }

        fun logDebug(tag: String, msg: String) {
            Log.d(tag, msg)
        }

        fun logError(tag: String, error: String) {
            events.clear()
            events["Error"] = error
            trackEvent(tag)
            Log.v(tag, error)
        }
    }
}