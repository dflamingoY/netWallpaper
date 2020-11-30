package com.mingo.baselibrary.injection.module

import android.app.Activity
import com.mingo.baselibrary.injection.ActivityScope
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val act: Activity) {
    @ActivityScope
    @Provides
    fun providesAct(): Activity {
        return act
    }

}