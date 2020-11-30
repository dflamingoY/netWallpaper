package com.mingo.baselibrary.rx

import io.reactivex.Observer
import io.reactivex.disposables.Disposable


open class BaseObserver<T> : Observer<T> {
    override fun onSubscribe(d: Disposable) {

    }

    override fun onNext(t: T) {

    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
    }

    override fun onComplete() {

    }
}