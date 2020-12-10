package com.gaqiujun.moment1.module.subjectDetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.gaqiujun.moment1.R
import com.gaqiujun.moment1.adapter.BaseAdapterHelper
import com.gaqiujun.moment1.adapter.QuickAdapter
import com.gaqiujun.moment1.entity.BaseBean
import com.gaqiujun.moment1.entity.SubjectDetailsData
import com.gaqiujun.moment1.injection.component.DaggerSubjectDetailsComponent
import com.gaqiujun.moment1.module.subjectDetails.presenter.SubjectDetailsPresenter
import com.gaqiujun.moment1.module.subjectDetails.view.SubjectDetailsView
import com.mingo.baselibrary.base.BaseMvpAct
import com.mingo.baselibrary.utils.AppTools
import kotlinx.android.synthetic.main.activity_subject_details.*
import kotlinx.android.synthetic.main.head_img.ivHeadView
import kotlinx.android.synthetic.main.head_img.view.*

class SubjectDetailsActivity : BaseMvpAct<SubjectDetailsPresenter>(), SubjectDetailsView {
    private lateinit var headView: View
    private lateinit var adapter: QuickAdapter<BaseBean>
    private val mData by lazy { ArrayList<BaseBean>() }
    override fun getLayoutId(): Int {
        return R.layout.activity_subject_details
    }

    override fun initView() {
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        headView = LayoutInflater.from(this).inflate(R.layout.head_img, recyclerView, false)
        val url = intent.getStringExtra("url")
        Glide.with(this)
            .load(url)
            .into(headView.ivHeadView)
        Glide.with(this)
            .load(url)
            .into(ivHeadView)
        ViewCompat.setTransitionName(ivHeadView, "anima")
    }

    override fun initData() {
        DaggerSubjectDetailsComponent.builder().activityComponent(actComponent).build().inject(this)
        presenter.view = this
        val width = AppTools.getWindowWidth(this)
        val height = (353 * 1.0f / (730 * 1.0f) * width).toInt()
        val headParams = ViewGroup.LayoutParams(width, height)
        headView.layoutParams = headParams
        val cardParams = cardView.layoutParams
        cardParams.height = height
        adapter = object : QuickAdapter<BaseBean>(this, R.layout.item_img, mData, headView) {
            val width_1 = AppTools.getWindowWidth(this@SubjectDetailsActivity) / 3
            val height_1 = ((355f / 200f * width_1 + 0.5f).toInt())
            val params = RelativeLayout.LayoutParams(width_1, height_1)

            override fun convert(helper: BaseAdapterHelper?, item: BaseBean?) {
                Glide.with(this@SubjectDetailsActivity)
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
        presenter.getSubjectList(intent.getStringExtra("id"))
        intent.getStringExtra("title").let {
            if (!it.isNullOrEmpty()) {
                tvTitle.text = it
            }
        }
    }

    override fun initEvent() {

    }

    override fun dataList(list: SubjectDetailsData?) {
        list?.let {
            it.list?.let {
                mData.addAll(it)
                adapter.notifyDataSetChanged()
            }
        }
    }
}