package com.gaqiujun.moment1.module.search.view

import com.gaqiujun.moment1.entity.BaseBean
import com.gaqiujun.moment1.entity.SearchTagBean
import com.mingo.baselibrary.mvp.BaseView

interface SearchWallpaperView : BaseView {

    fun showTag(list: List<SearchTagBean>?)

    fun showList(list: List<BaseBean>?)

    fun searchResult()


}