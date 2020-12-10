package com.gaqiujun.moment1.module.subjectDetails.presenter

import com.gaqiujun.moment1.entity.SubjectDetailsData
import com.gaqiujun.moment1.module.subjectDetails.model.SubjectDetailsModel
import com.gaqiujun.moment1.module.subjectDetails.view.SubjectDetailsView
import com.gaqiujun.moment1.net.isNetOk
import com.mingo.baselibrary.extention.execute
import com.mingo.baselibrary.mvp.BasePresenter
import com.mingo.baselibrary.net.procotol.BaseResp
import com.mingo.baselibrary.rx.BaseObserver
import javax.inject.Inject

class SubjectDetailsPresenter @Inject constructor() : BasePresenter<SubjectDetailsView>() {

    @Inject
    lateinit var model: SubjectDetailsModel

    fun getSubjectList(id: String?) {
        model.getSubjectData(id).execute(object : BaseObserver<BaseResp<SubjectDetailsData>>() {
            override fun onNext(t: BaseResp<SubjectDetailsData>) {
                if (t.msgCode.isNetOk()) {
                    view.dataList(t.body)
                }
            }
        }, provider)
    }
}