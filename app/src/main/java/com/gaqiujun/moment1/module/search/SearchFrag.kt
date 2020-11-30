package com.gaqiujun.moment1.module.search

import com.gaqiujun.moment1.R
import com.gaqiujun.moment1.module.search.presenter.SearchPresenter
import com.gaqiujun.moment1.module.search.view.SearchWallpaperView
import com.mingo.baselibrary.base.BaseMvpFrag

class SearchFrag : BaseMvpFrag<SearchPresenter>(), SearchWallpaperView {

    override fun injection() {

    }

    override fun getLayoutId(): Int {
        return R.layout.frag_search
    }

    override fun initView() {

    }

    override fun initData() {

    }

    override fun initEvent() {

    }
}