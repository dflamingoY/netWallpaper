package com.mingo.baselibrary.mvp

interface BaseView {
    fun showContent(any: Any?)
    fun showError(any: Any?)
    fun showProgress()
    fun showOffline()
}