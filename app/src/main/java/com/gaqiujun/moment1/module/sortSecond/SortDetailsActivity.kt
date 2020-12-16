package com.gaqiujun.moment1.module.sortSecond

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.gaqiujun.moment1.R
import com.gaqiujun.moment1.entity.SortDataBody
import com.gaqiujun.moment1.injection.component.DaggerSortDetailsComponent
import com.gaqiujun.moment1.module.sortSecond.presenter.SortDetailPresenter
import com.gaqiujun.moment1.module.sortSecond.view.SortDetailView
import com.gaqiujun.moment1.widget.SortListView
import com.gaqiujun.moment1.widget.SortTypeListView
import com.mingo.baselibrary.base.BaseMvpAct
import kotlinx.android.synthetic.main.activity_sort_details.*

class SortDetailsActivity : BaseMvpAct<SortDetailPresenter>(), SortDetailView {
    private val titles = arrayOf("最新", "最热", "分类")
    private lateinit var childList: ArrayList<View>
    override fun getLayoutId(): Int {
        return R.layout.activity_sort_details
    }

    override fun initView() {
        DaggerSortDetailsComponent.builder().activityComponent(actComponent).build().inject(this)
        presenter.view = this
        setSupportActionBar(toolbar)
        childList = arrayListOf()
    }

    override fun initData() {
        val id = intent.getStringExtra("id")
        val title = intent.getStringExtra("title")
        tvTitle.text = title
        childList.add(SortListView(this, id, 0))
        childList.add(SortListView(this, id, 1))
        childList.add(SortTypeListView(this, id))
        presenter.getSortList(id)
    }

    override fun initEvent() {

    }

    override fun showIdInfo(data: SortDataBody) {
        if (data.cats != null && data.cats!!.isNotEmpty()) {
            viewPager.adapter = SortAdapter(3)
        } else {
            viewPager.adapter = SortAdapter(2)
        }
        pagerSliding.setViewPager(viewPager)
    }

    private inner class SortAdapter(val size: Int) : PagerAdapter() {

        override fun getCount(): Int {
            return size
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            container.addView(childList[position])
            return childList[position]
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
//            super.destroyItem(container, position, `object`)
            container.removeView(`object` as View?)
        }

        override fun getPageTitle(position: Int): CharSequence {
            return titles[position]
        }
    }
}