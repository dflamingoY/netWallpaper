package com.gaqiujun.moment1.module.sort.view

import com.gaqiujun.moment1.entity.BaseBean
import com.mingo.baselibrary.mvp.BaseView

interface SortView : BaseView {
    fun sortList(list: List<BaseBean>?)
}