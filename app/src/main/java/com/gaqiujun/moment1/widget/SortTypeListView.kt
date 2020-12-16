package com.gaqiujun.moment1.widget

import android.annotation.SuppressLint
import android.content.Context
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.gaqiujun.moment1.R
import com.gaqiujun.moment1.adapter.BaseAdapterHelper
import com.gaqiujun.moment1.adapter.QuickAdapter
import com.gaqiujun.moment1.entity.BaseBean
import com.gaqiujun.moment1.entity.SortDataBody
import com.gaqiujun.moment1.module.sortSecond.SortDetailsActivity
import com.gaqiujun.moment1.module.sortSecond.model.ChildViewModel
import com.gaqiujun.moment1.net.isNetOk
import com.mingo.baselibrary.extention.execute
import com.mingo.baselibrary.net.procotol.BaseResp
import com.mingo.baselibrary.rx.BaseObserver
import com.mingo.baselibrary.utils.AppTools
import com.mingo.baselibrary.widget.BaseLayout
import com.trello.rxlifecycle3.LifecycleProvider
import kotlinx.android.synthetic.main.layout_sort_list.view.*
import org.jetbrains.anko.startActivity

@SuppressLint("ViewConstructor")
class SortTypeListView(context: Context, val id: String?) : BaseLayout(context) {
    private var model = ChildViewModel()
    private var adapter: QuickAdapter<BaseBean>
    private val mData by lazy { ArrayList<BaseBean>() }
    private val heights by lazy { ArrayList<Int>() }
    private var itemWidth = 0

    override fun getLayoutId(): Int {
        return R.layout.layout_sort_list
    }

    init {
        adapter = object : QuickAdapter<BaseBean>(context, R.layout.item_img, mData) {

            override fun convert(helper: BaseAdapterHelper?, item: BaseBean?) {
                val params = helper!!.getView(R.id.iv_img).layoutParams
                params.width = itemWidth
                params.height = heights[helper.itemView.tag as Int]
                Glide.with(context)
                    .load(item!!.url + "@305,300.jpg")
                    .into(helper.getImageView(R.id.iv_img))
            }
        }
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = adapter
        swipeRefresh.isRefreshing = false
        adapter.setOnItemClickListener { _, position ->
            context.startActivity<SortDetailsActivity>(
                "id" to mData[position].id,
                "title" to mData[position].title
            )
        }
        getData()
    }

    private fun getData() {
        model.getCatList(id).execute(
            object : BaseObserver<BaseResp<SortDataBody>>() {
                override fun onNext(t: BaseResp<SortDataBody>) {
                    if (t.msgCode.isNetOk()) {
                        t.body.cats?.let {
                            getRandomHeight(it.size)
                            mData.addAll(it)
                            adapter.notifyDataSetChanged()
                        }
                    }
                }

                override fun onError(e: Throwable) {
                }
            },
            context as LifecycleProvider<*>
        )
    }

    private fun getRandomHeight(size: Int) { //得到随机item的高度
        itemWidth = (AppTools.getWindowWidth(context) / 2f + 0.5f - 15).toInt()
        if (size % 2 == 0) {
            for (i in 0 until size) {
                if (i == 0 || i == size - 1) {
                    val height = (305 * 1.0f / (300 * 1.0f) * itemWidth).toInt()
                    heights.add(height)
                } else {
                    val height = (350 * 1.0f / (300 * 1.0f) * itemWidth).toInt()
                    heights.add(height)
                }
            }
        } else {
            for (i in 0 until size) {
                if (i == 0) {
                    val height = (305 * 1.0f / (300 * 1.0f) * itemWidth).toInt()
                    heights.add(height)
                } else {
                    val height = (350 * 1.0f / (300 * 1.0f) * itemWidth).toInt()
                    heights.add(height)
                }
            }
        }
    }


}