package com.gaqiujun.moment1.module.selected.view

import com.gaqiujun.moment1.entity.HomeData
import com.mingo.baselibrary.mvp.BaseView

interface SelectedView : BaseView {
    fun showData(homeData: HomeData)
}