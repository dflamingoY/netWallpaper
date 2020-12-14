package com.gaqiujun.moment1.module.sort.presenter

import com.gaqiujun.moment1.entity.BaseBean
import com.gaqiujun.moment1.module.sort.model.SortModel
import com.gaqiujun.moment1.module.sort.view.SortView
import com.mingo.baselibrary.extention.execute
import com.mingo.baselibrary.mvp.BasePresenter
import com.mingo.baselibrary.net.procotol.BaseResp
import com.mingo.baselibrary.rx.BaseObserver
import javax.inject.Inject

class SortPresenter @Inject constructor() : BasePresenter<SortView>() {
    @Inject
    lateinit var model: SortModel

    fun getSortList() {
        model.getSort().execute(object : BaseObserver<BaseResp<List<BaseBean>>>() {
            override fun onNext(t: BaseResp<List<BaseBean>>) {

            }

            override fun onError(e: Throwable) {

            }
        }, provider)
    }

}