package com.mingo.baselibrary.mvp

import android.content.Context
import com.trello.rxlifecycle3.LifecycleProvider
import javax.inject.Inject

open class BasePresenter<T : BaseView> {
    lateinit var view: T

    @Inject
    lateinit var provider: LifecycleProvider<*>

    @Inject
    lateinit var context: Context
}