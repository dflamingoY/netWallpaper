package com.mingo.baselibrary.utils

import android.util.Log

class LogUtils {

    companion object {
        private const val debug = true

        @JvmStatic
        fun d(tag: String, text: String) {
            if (debug)
                Log.d(tag, text)
        }

        @JvmStatic
        fun i(tag: String, text: String) {
            if (debug)
                Log.i(tag, text)
        }

        @JvmStatic
        fun e(tag: String, text: String) {
            if (debug)
                Log.e(tag, text)
        }

    }
}