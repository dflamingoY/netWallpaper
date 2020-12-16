package com.gaqiujun.moment1.module.sort

import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.gaqiujun.moment1.R
import com.gaqiujun.moment1.adapter.BaseAdapterHelper
import com.gaqiujun.moment1.adapter.QuickAdapter
import com.gaqiujun.moment1.entity.BaseBean
import com.gaqiujun.moment1.injection.component.DaggerSortComponent
import com.gaqiujun.moment1.module.sort.presenter.SortPresenter
import com.gaqiujun.moment1.module.sort.view.SortView
import com.gaqiujun.moment1.module.sortSecond.SortDetailsActivity
import com.mingo.baselibrary.base.BaseMvpFrag
import com.mingo.baselibrary.utils.AppTools
import kotlinx.android.synthetic.main.frag_recycler.*
import org.jetbrains.anko.support.v4.startActivity

class SortFrag : BaseMvpFrag<SortPresenter>(), SortView {
    private val mData by lazy { ArrayList<BaseBean>() }
    private lateinit var adapter: QuickAdapter<BaseBean>
    private val heights by lazy { ArrayList<Int>() }
    private var width = 0

    override fun injection() {
        DaggerSortComponent.builder().activityComponent(activityComponent).build().inject(this)
        presenter.view = this
    }

    override fun getLayoutId(): Int {
        return R.layout.frag_recycler
    }

    override fun initView() {
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        swipeRefresh.isEnabled = false
    }

    override fun initData() {
        adapter = object : QuickAdapter<BaseBean>(activity, R.layout.item_img, mData) {
            override fun convert(helper: BaseAdapterHelper?, item: BaseBean?) {
                val params = helper!!.getView(R.id.iv_img).layoutParams
                params.width = width
                params.height = heights[helper.itemView.tag as Int]
                Glide.with(activity!!)
                    .load(item!!.url + "@305,300.jpg")
                    .into(helper.getImageView(R.id.iv_img))
            }
        }
        recyclerView.adapter = adapter
        presenter.getSortList()
    }

    override fun initEvent() {
        adapter.setOnItemClickListener { _, position ->
            startActivity<SortDetailsActivity>(
                "id" to mData[position].id,
                "title" to mData[position].title
            )
        }
    }

    override fun sortList(list: List<BaseBean>?) {
        list?.let {
            getRandomHeight(it.size)
            mData.addAll(it)
            mData.reverse()
            adapter.notifyDataSetChanged()
        }
    }

    private fun getRandomHeight(size: Int) { //得到随机item的高度
        width = (AppTools.getWindowWidth(requireContext()) / 2f + 0.5f - 15).toInt()
        if (size % 2 == 0) {
            for (i in 0 until size) {
                if (i == 0 || i == size - 1) {
                    val height = (305 * 1.0f / (300 * 1.0f) * width).toInt()
                    heights.add(height)
                } else {
                    val height = (350 * 1.0f / (300 * 1.0f) * width).toInt()
                    heights.add(height)
                }
            }
        } else {
            for (i in 0 until size) {
                if (i == 0) {
                    val height = (305 * 1.0f / (300 * 1.0f) * width).toInt()
                    heights.add(height)
                } else {
                    val height = (350 * 1.0f / (300 * 1.0f) * width).toInt()
                    heights.add(height)
                }
            }
        }
    }

}