package com.gaqiujun.moment1.module.subjectDetails.view

import com.gaqiujun.moment1.entity.SubjectDetailsData
import com.mingo.baselibrary.mvp.BaseView

interface SubjectDetailsView : BaseView {

    fun dataList(list: SubjectDetailsData?)

}