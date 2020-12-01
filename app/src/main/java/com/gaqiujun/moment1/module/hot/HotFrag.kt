package com.gaqiujun.moment1.module.hot

import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.gaqiujun.moment1.R
import com.gaqiujun.moment1.adapter.BaseAdapterHelper
import com.gaqiujun.moment1.adapter.QuickAdapter
import com.gaqiujun.moment1.entity.BaseBean
import com.gaqiujun.moment1.injection.component.DaggerHotComponent
import com.gaqiujun.moment1.module.hot.presenter.HotPresenter
import com.gaqiujun.moment1.module.hot.view.HotView
import com.mingo.baselibrary.base.BaseMvpFrag
import kotlinx.android.synthetic.main.frag_recycler.*

/**
 * type 0 热门  1 最新
 */
class HotFrag(private val type: Int) : BaseMvpFrag<HotPresenter>(), HotView {
    private lateinit var adapter: QuickAdapter<BaseBean>
    private val mData by lazy { ArrayList<BaseBean>() }

    override fun getLayoutId(): Int {
        return R.layout.frag_recycler
    }

    override fun initView() {
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
    }

    override fun initData() {
        adapter = object : QuickAdapter<BaseBean>(requireContext(), R.layout.item_img, mData) {
            override fun convert(helper: BaseAdapterHelper?, item: BaseBean?) {
                Glide.with(requireContext())
                    .load(item!!.url + "@200,355.jpg")
                    .into(helper!!.getImageView(R.id.iv_img))
            }
        }
        recyclerView.adapter = adapter
        presenter.getList()
    }

    override fun initEvent() {

    }

    override fun injection() {
        DaggerHotComponent.builder().activityComponent(activityComponent).build().inject(this)
        presenter.view = this
        presenter.setHotType(type)
    }

    override fun showList(list: List<BaseBean>?) {
        list?.let {
            mData.addAll(it)
            adapter.notifyDataSetChanged()
        }
    }
}