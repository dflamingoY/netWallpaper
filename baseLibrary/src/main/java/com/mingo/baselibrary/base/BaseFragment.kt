package com.mingo.baselibrary.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trello.rxlifecycle3.components.support.RxFragment
import java.util.concurrent.atomic.AtomicBoolean

abstract class BaseFragment : RxFragment() {

    private var mView: View? = null
    private var atomic = AtomicBoolean(false)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mView?.let {
                (it.parent as ViewGroup).removeAllViews()
                it
            } == null) {
            mView = inflater.inflate(getLayoutId(), null)
        }
        return mView
    }

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

}