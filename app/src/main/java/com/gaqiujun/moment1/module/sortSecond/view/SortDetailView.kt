package com.gaqiujun.moment1.module.sortSecond.view

import com.gaqiujun.moment1.entity.SortDataBody
import com.mingo.baselibrary.mvp.BaseView

interface SortDetailView : BaseView {
    fun showIdInfo(data: SortDataBody)
}