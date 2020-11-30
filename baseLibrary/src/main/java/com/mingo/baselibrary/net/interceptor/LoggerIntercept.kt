package com.mingo.baselibrary.net.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.nio.charset.Charset

/**
 * 网络请求的log
 */
class LoggerIntercept : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request()).apply {
            Log.d(
                "NetLgo",
                "${chain.call().request().url}   result: " + body?.source()?.let {
                    it.request(Long.MAX_VALUE)
                    it.buffer.clone()
                        .readString(Charset.forName("utf-8"))
                }
            )
        }
    }
}