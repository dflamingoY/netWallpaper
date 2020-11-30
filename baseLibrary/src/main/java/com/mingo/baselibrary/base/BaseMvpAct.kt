package com.mingo.baselibrary.base

import android.os.Bundle
import com.mingo.baselibrary.injection.component.ActivityComponent
import com.mingo.baselibrary.injection.component.DaggerActivityComponent
import com.mingo.baselibrary.injection.module.ActivityModule
import com.mingo.baselibrary.injection.module.LifecycleProviderModule
import com.mingo.baselibrary.mvp.BasePresenter
import com.mingo.baselibrary.mvp.BaseView
import javax.inject.Inject

abstract class BaseMvpAct<T : BasePresenter<*>> : BaseSuperAct(), BaseView {

    @Inject
    lateinit var presenter: T
    lateinit var actComponent: ActivityComponent
    override fun onCreate(savedInstanceState: Bundle?) {
        initInjection()
        super.onCreate(savedInstanceState)
    }

    private fun initInjection() {
        actComponent = DaggerActivityComponent.builder().activityModule(ActivityModule(this))
            .lifecycleProviderModule(LifecycleProviderModule(this))
            .appComponent((application as BaseApp).appModule)
            .build()
    }

    override fun showContent(any: Any?) {

    }

    override fun showError(any: Any?) {

    }

    override fun showOffline() {

    }

    override fun showProgress() {

    }
}