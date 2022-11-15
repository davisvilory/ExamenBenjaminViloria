package com.mobile.examenbenjaminviloria.utils

import androidx.fragment.app.Fragment
import com.mobile.examenbenjaminviloria.MainActivity

open class BaseFragment : Fragment() {
    private val thisTag = "Log_BaseFragment"

    fun showProgress(show: Boolean) {
        try {
            if (context != null && context is MainActivity)
                (context as MainActivity).showProgressFragment(show)
        } catch (ex: Exception) {
            Global.logError(thisTag, ex.toString())
        }
    }

    override fun onDetach() {
        try {
            if (context != null && context is MainActivity)
                (context as MainActivity).showProgressFragment(false)
        } catch (ex: Exception) {
            Global.logError(thisTag, ex.toString())
        }
        super.onDetach()
    }
}