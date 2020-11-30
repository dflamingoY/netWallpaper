package com.mingo.baselibrary.injection.component

import android.app.Activity
import android.content.Context
import com.mingo.baselibrary.injection.ActivityScope
import com.mingo.baselibrary.injection.module.ActivityModule
import com.mingo.baselibrary.injection.module.LifecycleProviderModule
import com.trello.rxlifecycle3.LifecycleProvider
import dagger.Component

@ActivityScope
@Component(
    modules = [ActivityModule::class, LifecycleProviderModule::class],
    dependencies = [AppComponent::class]
)
interface ActivityComponent {
    fun activity(): Activity
    fun lifeCycleProvider(): LifecycleProvider<*>
    fun context(): Context
}