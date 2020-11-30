package com.mingo.baselibrary.net.interceptor

import android.content.Context
import com.mingo.baselibrary.utils.*
import okhttp3.Interceptor
import okhttp3.Response

class RequestHeadInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val header = chain.request().newBuilder()
        header.addHeader("clientType", "android")
            .addHeader("Authorization", SPUtils.getString(context, USER_TOKEN, ""))
            .addHeader("device", AppTools.getIdentity(context))
        return chain.proceed(header.build())
    }
}