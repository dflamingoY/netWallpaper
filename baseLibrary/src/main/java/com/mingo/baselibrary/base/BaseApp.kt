package com.mingo.baselibrary.base

import android.app.Application
import com.mingo.baselibrary.injection.component.AppComponent
import com.mingo.baselibrary.injection.component.DaggerAppComponent
import com.mingo.baselibrary.injection.module.ApplicationModule

open class BaseApp : Application() {
    lateinit var appModule: AppComponent

    override fun onCreate() {
        super.onCreate()
        appModule = DaggerAppComponent.builder().applicationModule(ApplicationModule(this)).build()
    }

}