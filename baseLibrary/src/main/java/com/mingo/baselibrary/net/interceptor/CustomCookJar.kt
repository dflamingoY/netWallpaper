package com.mingo.baselibrary.net.interceptor

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import java.util.*

class CustomCookJar : CookieJar {
    private val cookieStore = HashMap<String, List<Cookie>>()
    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        val cookies = cookieStore[url.host]
        return cookies ?: arrayListOf()
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        try {
            cookieStore[url.host] = cookies
        } catch (e: Exception) {
        }
    }
}