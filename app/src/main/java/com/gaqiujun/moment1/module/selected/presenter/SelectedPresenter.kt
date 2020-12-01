package com.gaqiujun.moment1.module.selected.presenter

import com.gaqiujun.moment1.entity.HomeData
import com.gaqiujun.moment1.module.selected.model.SelectedModel
import com.gaqiujun.moment1.module.selected.view.SelectedView
import com.gaqiujun.moment1.net.isNetOk
import com.mingo.baselibrary.extention.execute
import com.mingo.baselibrary.mvp.BasePresenter
import com.mingo.baselibrary.net.procotol.BaseResp
import com.mingo.baselibrary.rx.BaseObserver
import javax.inject.Inject

class SelectedPresenter @Inject constructor() : BasePresenter<SelectedView>() {

    @Inject
    lateinit var model: SelectedModel

    fun getHomeData() {
        model.getHomeData().execute(object : BaseObserver<BaseResp<HomeData>>() {
            override fun onNext(t: BaseResp<HomeData>) {
                if (t.msgCode.isNetOk()) {
                    view.showData(t.body)
                }
            }
        }, provider)
    }

}