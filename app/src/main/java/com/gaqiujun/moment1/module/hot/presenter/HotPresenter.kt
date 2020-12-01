package com.gaqiujun.moment1.module.hot.presenter

import com.gaqiujun.moment1.entity.BaseBean
import com.gaqiujun.moment1.module.hot.model.HotModel
import com.gaqiujun.moment1.module.hot.view.HotView
import com.gaqiujun.moment1.net.isNetOk
import com.mingo.baselibrary.extention.execute
import com.mingo.baselibrary.mvp.BasePresenter
import com.mingo.baselibrary.net.procotol.BaseResp
import com.mingo.baselibrary.rx.BaseObserver
import javax.inject.Inject

class HotPresenter @Inject constructor() : BasePresenter<HotView>() {

    @Inject
    lateinit var model: HotModel
    private var current = 0
    private var hotType = 0

    fun setHotType(type: Int) {
        hotType = type
    }

    fun getList() {
        model.getList(if (hotType == 0) "pop/$current.do" else " newest/$current.do")
            .execute(object : BaseObserver<BaseResp<List<BaseBean>>>() {
                override fun onNext(t: BaseResp<List<BaseBean>>) {
                    if (t.msgCode.isNetOk()) {
                        view.showList(t.body)
                    }
                }
            }, provider)
    }

}