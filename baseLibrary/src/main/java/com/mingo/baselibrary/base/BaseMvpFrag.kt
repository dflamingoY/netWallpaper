package com.mingo.baselibrary.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mingo.baselibrary.injection.component.ActivityComponent
import com.mingo.baselibrary.injection.component.DaggerActivityComponent
import com.mingo.baselibrary.injection.module.ActivityModule
import com.mingo.baselibrary.injection.module.LifecycleProviderModule
import com.mingo.baselibrary.mvp.BasePresenter
import com.mingo.baselibrary.mvp.BaseView
import com.trello.rxlifecycle3.components.support.RxFragment
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

abstract class BaseMvpFrag<T : BasePresenter<*>> : RxFragment(), BaseView {
    @Inject
    lateinit var presenter: T
    private var mView: View? = null
    private var atomic = AtomicBoolean(false)
    lateinit var activityComponent: ActivityComponent

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mView?.let {
                (it.parent as ViewGroup).removeAllViews()
                it
            } == null) {
            initInjection()
            injection()
            mView = inflater.inflate(getLayoutId(), null)
        }
        return mView
    }

    private fun initInjection() {
        activityComponent = DaggerActivityComponent.builder()
            .appComponent((activity!!.application as BaseApp).appModule)
            .activityModule(ActivityModule(activity!!))
            .lifecycleProviderModule(LifecycleProviderModule(this))
            .build()
    }

    abstract fun injection()

    abstract fun getLayoutId(): Int

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!atomic.get()) {
            atomic.set(true)
            initView()
            initData()
            initEvent()
        }
    }

    abstract fun initView()
    abstract fun initData()
    abstract fun initEvent()

    override fun showContent(any: Any?) {

    }

    override fun showError(any: Any?) {

    }

    override fun showProgress() {

    }

    override fun showOffline() {

    }
}