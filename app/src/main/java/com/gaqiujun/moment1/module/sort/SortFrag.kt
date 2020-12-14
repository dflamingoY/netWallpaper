package com.gaqiujun.moment1.module.sort

import com.gaqiujun.moment1.R
import com.gaqiujun.moment1.injection.component.DaggerSortComponent
import com.gaqiujun.moment1.module.sort.presenter.SortPresenter
import com.gaqiujun.moment1.module.sort.view.SortView
import com.mingo.baselibrary.base.BaseMvpFrag

class SortFrag : BaseMvpFrag<SortPresenter>(), SortView {

    override fun injection() {
        DaggerSortComponent.builder().activityComponent(activityComponent).build().inject(this)
        presenter.view = this
    }

    override fun getLayoutId(): Int {
        return R.layout.frag_recycler
    }

    override fun initView() {

    }

    override fun initData() {

    }

    override fun initEvent() {

    }

}