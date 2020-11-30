package com.mingo.baselibrary.injection.module

import com.mingo.baselibrary.injection.ActivityScope
import com.trello.rxlifecycle3.LifecycleProvider
import dagger.Module
import dagger.Provides

@Module
class LifeCycleModule(private val lifeCycle: LifecycleProvider<*>) {
    @ActivityScope
    @Provides
    fun providesLifeCycle(): LifecycleProvider<*> {
        return lifeCycle
    }
}