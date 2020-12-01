package com.gaqiujun.moment1.core

import com.mingo.baselibrary.base.BaseApp
import com.mingo.baselibrary.net.HttpServer

class App : BaseApp() {
    override fun onCreate() {
        super.onCreate()
        HttpServer.init(applicationContext)
    }
}