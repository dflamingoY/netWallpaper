package com.gaqiujun.moment1.module.selected

import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.gaqiujun.moment1.R
import com.gaqiujun.moment1.adapter.BaseAdapterHelper
import com.gaqiujun.moment1.adapter.QuickAdapter
import com.gaqiujun.moment1.entity.BaseBean
import com.gaqiujun.moment1.entity.HomeData
import com.gaqiujun.moment1.injection.component.DaggerSelectedComponent
import com.gaqiujun.moment1.module.details.PicDetailsActivity
import com.gaqiujun.moment1.module.selected.presenter.SelectedPresenter
import com.gaqiujun.moment1.module.selected.view.SelectedView
import com.gaqiujun.moment1.widget.CircleCardImageView
import com.mingo.baselibrary.base.BaseMvpFrag
import com.mingo.baselibrary.utils.AppTools
import kotlinx.android.synthetic.main.frag_recycler.*
import kotlinx.android.synthetic.main.head_home_banner.view.*
import org.jetbrains.anko.support.v4.startActivity

class SelectedFrag : BaseMvpFrag<SelectedPresenter>(), SelectedView {
    private lateinit var headView: View
    private lateinit var adapter: QuickAdapter<BaseBean>
    private val mData by lazy { ArrayList<BaseBean>() }
    override fun injection() {
        DaggerSelectedComponent.builder().activityComponent(activityComponent).build().inject(this)
        presenter.view = this
    }

    override fun getLayoutId(): Int {
        return R.layout.frag_recycler
    }

    override fun initView() {
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        headView = LayoutInflater.from(requireContext())
                .inflate(R.layout.head_home_banner, recyclerView, false)
    }

    override fun initData() {
        adapter =
                object : QuickAdapter<BaseBean>(requireContext(), R.layout.item_img, mData, headView) {

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
        (recyclerView.layoutManager as GridLayoutManager).spanSizeLookup =
                object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return if (adapter.isHeader(position)) 3 else 1
                    }
                }
        presenter.getHomeData()
    }

    override fun initEvent() {
        adapter.setOnItemClickListener { _, position ->
            startActivity<PicDetailsActivity>("data" to mData, "index" to position)
        }
        swipeRefresh.setOnRefreshListener {
            presenter.getHomeData()
        }
    }

    override fun showData(homeData: HomeData) {
        swipeRefresh.isRefreshing = false
        homeData.youBiGe?.let {
            mData.addAll(it)
            adapter.notifyDataSetChanged()
        }
        homeData.xiaoBians?.forEach {
            val view = CircleCardImageView(requireContext())
            view.showPic("${it.url}@400,400.jpg")
            headView.linearContainer.addView(view)
        }
    }
}