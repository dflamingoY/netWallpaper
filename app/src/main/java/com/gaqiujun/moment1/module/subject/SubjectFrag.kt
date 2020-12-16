package com.gaqiujun.moment1.module.subject

import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.gaqiujun.moment1.R
import com.gaqiujun.moment1.adapter.BaseAdapterHelper
import com.gaqiujun.moment1.adapter.BaseQuickAdapter
import com.gaqiujun.moment1.adapter.QuickAdapter
import com.gaqiujun.moment1.entity.BaseBean
import com.gaqiujun.moment1.injection.component.DaggerSubjectComponent
import com.gaqiujun.moment1.module.hot.view.HotView
import com.gaqiujun.moment1.module.subject.presenter.SubjectPresenter
import com.gaqiujun.moment1.net.jump2Subject
import com.mingo.baselibrary.base.BaseMvpFrag
import com.mingo.baselibrary.utils.AppTools
import kotlinx.android.synthetic.main.frag_recycler.*

class SubjectFrag : BaseMvpFrag<SubjectPresenter>(), HotView {
    private lateinit var adapter: QuickAdapter<BaseBean>
    private val mData by lazy { ArrayList<BaseBean>() }
    private var index = 0
    override fun getLayoutId(): Int {
        return R.layout.frag_recycler
    }

    override fun initView() {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun initData() {
        adapter = object : QuickAdapter<BaseBean>(requireContext(), R.layout.item_img, mData) {
            val width = AppTools.getWindowWidth(requireContext())
            val height: Int = ((353f / 730f * width + 0.5f).toInt())
            val params = RelativeLayout.LayoutParams(width, height)

            override fun convert(helper: BaseAdapterHelper?, item: BaseBean?) {
                Glide.with(requireContext())
                    .load(item!!.url + "@730,353.jpg")
                    .into(helper!!.getImageView(R.id.iv_img))
                helper.getImageView(R.id.iv_img).layoutParams = params
            }
        }
        recyclerView.adapter = adapter
        adapter.setLoadMoreEnable(
            recyclerView,
            recyclerView.layoutManager,
            LayoutInflater.from(activity).inflate(R.layout.view_loadmore, recyclerView, false)
        )
        adapter.setLoadStatue(BaseQuickAdapter.ELoadState.EMPTY)
        presenter.getList(index)
    }

    override fun initEvent() {
        adapter.setOnItemClickListener { view, position ->
            requireActivity().jump2Subject(
                mData[position].id,
                mData[position].url + "@730,353.jpg",
                mData[position].title,
                view.findViewById(R.id.iv_img)
            )
        }
        swipeRefresh.setOnRefreshListener {
            index = 0
            presenter.getList(index)
        }
        adapter.setOnLoadListener {
            presenter.getList(++index)
        }
    }

    override fun injection() {
        DaggerSubjectComponent.builder().activityComponent(activityComponent).build().inject(this)
        presenter.view = this
    }

    override fun showList(list: List<BaseBean>?) {
        swipeRefresh.isRefreshing = false
        adapter.setLoadStatue(BaseQuickAdapter.ELoadState.EMPTY)
        list?.let {
            if (index == 0) {
                mData.clear()
            }
            mData.addAll(it)
            adapter.notifyDataSetChanged()
            if (it.size >= 12) {
                adapter.setLoadStatue(BaseQuickAdapter.ELoadState.READY)
            }
        }
    }

    override fun showError(any: Any?) {
        adapter.setLoadStatue(BaseQuickAdapter.ELoadState.EMPTY)
        swipeRefresh.isRefreshing = false
    }
}