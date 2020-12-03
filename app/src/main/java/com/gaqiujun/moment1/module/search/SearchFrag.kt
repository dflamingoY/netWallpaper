package com.gaqiujun.moment1.module.search

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import com.gaqiujun.moment1.R
import com.gaqiujun.moment1.adapter.BaseAdapterHelper
import com.gaqiujun.moment1.adapter.QuickAdapter
import com.gaqiujun.moment1.entity.BaseBean
import com.gaqiujun.moment1.entity.SearchTagBean
import com.gaqiujun.moment1.injection.component.DaggerSearchComponent
import com.gaqiujun.moment1.module.search.presenter.SearchPresenter
import com.gaqiujun.moment1.module.search.view.SearchWallpaperView
import com.mingo.baselibrary.base.BaseMvpFrag
import com.mingo.baselibrary.utils.AppTools
import kotlinx.android.synthetic.main.frag_search.*

class SearchFrag : BaseMvpFrag<SearchPresenter>(), SearchWallpaperView {
    private lateinit var adapter: QuickAdapter<BaseBean>
    private val mData by lazy { ArrayList<BaseBean>() }

    override fun injection() {
        DaggerSearchComponent.builder().activityComponent(activityComponent).build().inject(this)
        presenter.view = this
    }

    override fun getLayoutId(): Int {
        return R.layout.frag_search
    }

    override fun initView() {
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
    }

    override fun initData() {
        adapter = object : QuickAdapter<BaseBean>(requireContext(), R.layout.item_img, mData) {
            override fun convert(helper: BaseAdapterHelper?, item: BaseBean?) {

            }
        }
        recyclerView.adapter = adapter
        presenter.getSearchTag()
    }

    override fun initEvent() {
        tvCancel.setOnClickListener {

        }
    }

    override fun showTag(list: List<SearchTagBean>?) {
        flowTables.removeAllViews()
        val mar = AppTools.dp2px(requireContext(), 5f).toInt()
        list?.forEach {
            val mGd = GradientDrawable()
            val tv = TextView(context)
            if (!TextUtils.isEmpty(it.color)) {
                val colors = it.color?.split(",")
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
            mGd.cornerRadius = 10.0f
            tv.text = it.title
            tv.gravity = Gravity.CENTER
            tv.setPadding(mar, mar, mar, mar)
            tv.textSize = 13f
            tv.setTextColor(Color.parseColor("#a3a3a3"))
            tv.background = mGd
            flowTables.addView(tv)
            tv.setOnClickListener { _ ->
                presenter.searchById(it.id)
            }
        }
    }

    override fun showList(list: List<BaseBean>?) {
        recyclerView.visibility = View.VISIBLE
        frameTag.visibility = View.GONE
        list?.let {
            mData.addAll(list)
            adapter.notifyDataSetChanged()
        }
    }

    override fun searchResult(list: List<SearchTagBean>?) {

    }
}