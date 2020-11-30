package com.mingo.baselibrary.extention

import com.trello.rxlifecycle3.LifecycleProvider
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Observable 扩展方法
 */
fun <T> Observable<T>.execute(subscriber: Observer<T>, provider: LifecycleProvider<*>) {
    this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .compose(provider.bindToLifecycle())
        .subscribe(subscriber)
}

/**
 * 扩张方法, 传入T -> 输出 R
 */
fun <T, R> Observable<T>.executeConvert(
    subscriber: Observer<R>,
    function: (T) -> R, provider: LifecycleProvider<*>
) {
    this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .compose(provider.bindToLifecycle())
        .map {
            function(it)
        }
        .subscribe(subscriber)
}