package com.gaqiujun.moment1.module.subject

import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.gaqiujun.moment1.R
import com.gaqiujun.moment1.adapter.BaseAdapterHelper
import com.gaqiujun.moment1.adapter.QuickAdapter
import com.gaqiujun.moment1.entity.BaseBean
import com.gaqiujun.moment1.injection.component.DaggerSubjectComponent
import com.gaqiujun.moment1.module.hot.view.HotView
import com.gaqiujun.moment1.module.subject.presenter.SubjectPresenter
import com.mingo.baselibrary.base.BaseMvpFrag
import com.mingo.baselibrary.utils.AppTools
import kotlinx.android.synthetic.main.frag_recycler.*

class SubjectFrag : BaseMvpFrag<SubjectPresenter>(), HotView {
    private lateinit var adapter: QuickAdapter<BaseBean>
    private val mData by lazy { ArrayList<BaseBean>() }

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
        presenter.getList()
    }

    override fun initEvent() {

    }

    override fun injection() {
        DaggerSubjectComponent.builder().activityComponent(activityComponent).build().inject(this)
        presenter.view = this
    }

    override fun showList(list: List<BaseBean>?) {
        list?.let {
            mData.addAll(it)
            adapter.notifyDataSetChanged()
        }
    }
}