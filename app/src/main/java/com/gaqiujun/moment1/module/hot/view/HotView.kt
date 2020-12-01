package com.gaqiujun.moment1.module.hot.view

import com.gaqiujun.moment1.entity.BaseBean
import com.mingo.baselibrary.mvp.BaseView

interface HotView : BaseView {
    fun showList(list: List<BaseBean>?)
}