package com.gaqiujun.moment1.module.hot

import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.gaqiujun.moment1.R
import com.gaqiujun.moment1.adapter.BaseAdapterHelper
import com.gaqiujun.moment1.adapter.BaseQuickAdapter
import com.gaqiujun.moment1.adapter.QuickAdapter
import com.gaqiujun.moment1.entity.BaseBean
import com.gaqiujun.moment1.injection.component.DaggerHotComponent
import com.gaqiujun.moment1.module.details.PicDetailsActivity
import com.gaqiujun.moment1.module.hot.presenter.HotPresenter
import com.gaqiujun.moment1.module.hot.view.HotView
import com.mingo.baselibrary.base.BaseMvpFrag
import com.mingo.baselibrary.utils.AppTools
import kotlinx.android.synthetic.main.frag_recycler.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * type 0 热门  1 最新
 */
class HotFrag(private val type: Int) : BaseMvpFrag<HotPresenter>(), HotView {
    private lateinit var adapter: QuickAdapter<BaseBean>
    private val mData by lazy { ArrayList<BaseBean>() }
    private var index = 0
    override fun getLayoutId(): Int {
        return R.layout.frag_recycler
    }

    override fun initView() {
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
    }

    override fun initData() {
        adapter = object : QuickAdapter<BaseBean>(requireContext(), R.layout.item_img, mData) {
            val width = AppTools.getWindowWidth(requireContext()) / 3
            val height: Int = ((355f / 200f * width + 0.5f).toInt())
            val params = RelativeLayout.LayoutParams(width, height)

            override fun convert(helper: BaseAdapterHelper?, item: BaseBean?) {
                Glide.with(requireContext())
                    .load(item!!.url + "@200,355.jpg")
                    .into(helper!!.getImageView(R.id.iv_img))
                helper.getImageView(R.id.iv_img).layoutParams = params
            }
        }
        recyclerView.adapter = adapter
        presenter.getList(index)
        adapter.setLoadMoreEnable(
            recyclerView,
            recyclerView.layoutManager,
            LayoutInflater.from(requireContext())
                .inflate(R.layout.view_loadmore, recyclerView, false)
        )
        adapter.setLoadStatue(BaseQuickAdapter.ELoadState.EMPTY)
    }

    override fun initEvent() {
        swipeRefresh.setOnRefreshListener {
            adapter.setLoadStatue(BaseQuickAdapter.ELoadState.EMPTY)
            index = 0
            presenter.getList(index)
        }
        adapter.setOnItemClickListener { _, position ->
            startActivity<PicDetailsActivity>("data" to mData, "index" to position)
        }
        adapter.setOnLoadListener {
            presenter.getList(++index)
        }
    }

    override fun injection() {
        DaggerHotComponent.builder().activityComponent(activityComponent).build().inject(this)
        presenter.view = this
        presenter.setHotType(type)
    }

    override fun showList(list: List<BaseBean>?) {
        swipeRefresh.isRefreshing = false
        list?.let {
            if (index == 0) {
                mData.clear()
            }
            if (it.size >= 24) {
                adapter.setLoadStatue(BaseQuickAdapter.ELoadState.READY)
            } else {
                adapter.setLoadStatue(BaseQuickAdapter.ELoadState.EMPTY)
            }
            mData.addAll(it)
            adapter.notifyDataSetChanged()
        }
    }

    override fun showError(any: Any?) {
        swipeRefresh.isRefreshing = false
        adapter.setLoadStatue(BaseQuickAdapter.ELoadState.EMPTY)
    }

}