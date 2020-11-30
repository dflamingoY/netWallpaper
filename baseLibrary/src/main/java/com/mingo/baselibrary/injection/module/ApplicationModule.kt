package com.mingo.baselibrary.injection.module

import android.content.Context
import com.mingo.baselibrary.base.BaseApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val context: BaseApp) {
    @Singleton
    @Provides
    fun application(): Context {
        return context
    }
}