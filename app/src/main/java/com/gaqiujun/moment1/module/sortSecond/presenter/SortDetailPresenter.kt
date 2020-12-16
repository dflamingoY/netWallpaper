package com.gaqiujun.moment1.module.sortSecond.presenter

import com.gaqiujun.moment1.entity.SortDataBody
import com.gaqiujun.moment1.module.sortSecond.model.SortDetailsModel
import com.gaqiujun.moment1.module.sortSecond.view.SortDetailView
import com.gaqiujun.moment1.net.isNetOk
import com.mingo.baselibrary.extention.execute
import com.mingo.baselibrary.mvp.BasePresenter
import com.mingo.baselibrary.net.procotol.BaseResp
import com.mingo.baselibrary.rx.BaseObserver
import javax.inject.Inject

class SortDetailPresenter @Inject constructor() : BasePresenter<SortDetailView>() {

    @Inject
    lateinit var model: SortDetailsModel

    fun getSortList(id: String?) {
        model.getSortDetails(id).execute(object : BaseObserver<BaseResp<SortDataBody>>() {
            override fun onNext(t: BaseResp<SortDataBody>) {
                if (t.msgCode.isNetOk()) {
                    view.showIdInfo(t.body)
                }
            }

            override fun onError(e: Throwable) {

            }
        }, provider)
    }
}