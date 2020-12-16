package com.gaqiujun.moment1.widget

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.gaqiujun.moment1.R
import com.gaqiujun.moment1.adapter.BaseAdapterHelper
import com.gaqiujun.moment1.adapter.BaseQuickAdapter
import com.gaqiujun.moment1.adapter.QuickAdapter
import com.gaqiujun.moment1.entity.BaseBean
import com.gaqiujun.moment1.entity.SortDataBody
import com.gaqiujun.moment1.module.details.PicDetailsActivity
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
class SortListView(context: Context, val id: String?, val type: Int) : BaseLayout(context) {
    private var adapter: QuickAdapter<BaseBean>
    private val mData by lazy { ArrayList<BaseBean>() }
    private var index = 0

    private var model = ChildViewModel()
    override fun getLayoutId(): Int {
        return R.layout.layout_sort_list
    }

    init {
        adapter = object : QuickAdapter<BaseBean>(context, R.layout.item_img, mData) {
            val width = AppTools.getWindowWidth(context) / 3
            val height: Int = ((355f / 200f * width + 0.5f).toInt())
            val params = RelativeLayout.LayoutParams(width, height)

            override fun convert(helper: BaseAdapterHelper?, item: BaseBean?) {
                Glide.with(context)
                    .load(item!!.url + "@200,355.jpg")
                    .into(helper!!.getImageView(R.id.iv_img))
                helper.getImageView(R.id.iv_img).layoutParams = params
            }
        }
        recyclerView.layoutManager = GridLayoutManager(context, 3)
        recyclerView.adapter = adapter
        getData()
        adapter.setOnItemClickListener { _, position ->
            context.startActivity<PicDetailsActivity>("data" to mData, "index" to position)
        }
        adapter.setLoadMoreEnable(
            recyclerView,
            recyclerView.layoutManager,
            LayoutInflater.from(context).inflate(R.layout.view_loadmore, recyclerView, false)
        )
        adapter.setLoadStatue(BaseQuickAdapter.ELoadState.EMPTY)
        adapter.setOnLoadListener {
            index++
            getData()
        }
        swipeRefresh.setOnRefreshListener {
            index = 0
            getData()
        }
    }

    private fun getData() {
        model.getChildList(id, index, type).execute(
            object : BaseObserver<BaseResp<SortDataBody>>() {
                override fun onNext(t: BaseResp<SortDataBody>) {
                    adapter.setLoadStatue(BaseQuickAdapter.ELoadState.EMPTY)
                    swipeRefresh.isRefreshing = false
                    if (t.msgCode.isNetOk()) {
                        t.body.list?.let {
                            if (index == 0) {
                                mData.clear()
                            }
                            mData.addAll(it)
                            if (it.size >= 12) {
                                adapter.setLoadStatue(BaseQuickAdapter.ELoadState.READY)
                            }
                            adapter.notifyDataSetChanged()
                        }
                    }
                }

                override fun onError(e: Throwable) {
                    swipeRefresh.isRefreshing = false
                    adapter.setLoadStatue(BaseQuickAdapter.ELoadState.EMPTY)
                }
            },
            context as LifecycleProvider<*>
        )
    }

}