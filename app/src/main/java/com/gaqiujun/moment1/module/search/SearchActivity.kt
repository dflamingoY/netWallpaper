package com.gaqiujun.moment1.module.search

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.gaqiujun.moment1.R
import com.gaqiujun.moment1.entity.BaseBean
import com.gaqiujun.moment1.entity.SearchTagBean
import com.gaqiujun.moment1.injection.component.DaggerSearchActComponent
import com.gaqiujun.moment1.module.search.presenter.SearchPresenter
import com.gaqiujun.moment1.module.search.view.SearchWallpaperView
import com.gaqiujun.moment1.widget.dTag.TagsAdapter
import com.mingo.baselibrary.base.BaseMvpAct
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseMvpAct<SearchPresenter>(), SearchWallpaperView {
    private lateinit var tagAdapter: TagsAdapter
    private val mData by lazy { ArrayList<SearchTagBean>() }

    override fun getLayoutId(): Int {
        return R.layout.activity_search
    }

    override fun initView() {
        setTransparentStatusBar()
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
                tv.textSize = 10f
                val mGd = GradientDrawable()
                if (!TextUtils.isEmpty(mData[position].color)) {
                    val colors = mData[position].color?.split(",")
                    mGd.setColor(
                        Color.parseColor(
                            "#" + Integer.toHexString(
                                colors!![0].toInt()
                            ) + Integer.toHexString(colors[1].toInt()) + Integer.toHexString(
                                colors[2].toInt()
                            )
                        )
                    )
                } else {
                    mGd.setColor(Color.WHITE)
                }
                tv.setPadding(10, 5, 10, 5)
                mGd.cornerRadius = 10.0f
                tv.background = mGd
                return tv
            }

            override fun getItem(position: Int): Any {
                return mData[position]
            }

            override fun getPopularity(position: Int): Int {
                return 2
            }

            override fun onThemeColorChanged(view: View?, themeColor: Int, alpha: Float) {
            }
        }
        tagCloudView.setAdapter(tagAdapter)
        tagCloudView.setManualScroll(false)
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