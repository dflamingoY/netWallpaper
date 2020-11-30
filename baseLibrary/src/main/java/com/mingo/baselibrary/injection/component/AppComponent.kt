package com.mingo.baselibrary.injection.component

import android.content.Context
import com.mingo.baselibrary.injection.ActivityScope
import com.mingo.baselibrary.injection.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface AppComponent {
    fun context(): Context
}