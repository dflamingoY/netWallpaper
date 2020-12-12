package com.gaqiujun.moment1.module.search

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.gaqiujun.moment1.R
import com.gaqiujun.moment1.entity.BaseBean
import com.gaqiujun.moment1.entity.SearchTagBean
import com.gaqiujun.moment1.injection.component.DaggerSearchActComponent
import com.gaqiujun.moment1.module.search.presenter.SearchPresenter
import com.gaqiujun.moment1.module.search.view.SearchWallpaperView
import com.mingo.baselibrary.base.BaseMvpAct
import com.moxun.tagcloudlib.view.TagsAdapter
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseMvpAct<SearchPresenter>(), SearchWallpaperView {
    private lateinit var tagAdapter: TagsAdapter
    private val mData by lazy { ArrayList<SearchTagBean>() }
    override fun getLayoutId(): Int {
        return R.layout.activity_search
    }

    override fun initView() {
        DaggerSearchActComponent.builder().activityComponent(actComponent).build().inject(this)
        presenter.view = this
    }

    override fun initData() {
        tagAdapter = object : TagsAdapter() {
            override fun getCount(): Int {
                return mData.size
            }

            override fun getView(context: Context?, position: Int, parent: ViewGroup?): View {
                val tv = TextView(this@SearchActivity)
                tv.text = mData[position].title
                return tv
            }

            override fun getItem(position: Int): Any {
                return mData[position]
            }

            override fun getPopularity(position: Int): Int {
                return 10
            }

            override fun onThemeColorChanged(view: View?, themeColor: Int) {

            }

        }
        tagCloudView.setAdapter(tagAdapter)
        presenter.getSearchTag()
    }

    override fun initEvent() {

    }

    override fun showTag(list: List<SearchTagBean>?) {
        list?.let {
            mData.addAll(it)
            tagAdapter.notifyDataSetChanged()
        }
    }

    override fun showList(list: List<BaseBean>?) {

    }

    override fun searchResult(list: List<SearchTagBean>?) {

    }
}