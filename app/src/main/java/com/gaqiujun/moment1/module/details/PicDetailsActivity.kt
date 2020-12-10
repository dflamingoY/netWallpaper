package com.gaqiujun.moment1.module.details

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gaqiujun.moment1.R
import com.gaqiujun.moment1.entity.BaseBean
import com.gaqiujun.moment1.module.details.presenter.PicDetailPresenter
import com.gaqiujun.moment1.module.details.view.PicDetailsView
import com.mingo.baselibrary.base.BaseMvpAct
import kotlinx.android.synthetic.main.activity_pic_details.*

class PicDetailsActivity : BaseMvpAct<PicDetailPresenter>(), PicDetailsView {
    private val mData by lazy { ArrayList<BaseBean>() }
    private val images by lazy { images() }

    override fun getLayoutId(): Int {
        return R.layout.activity_pic_details
    }

    private fun images() = Array(4) {
        ImageView(this)
    }

    override fun initView() {
        setTransparentStatusBar()
    }

    override fun initData() {
        (intent.getSerializableExtra("data") as ArrayList<BaseBean>?)?.let {
            mData.addAll(it)
        }
        viewPager.adapter = ImgAdapter()
        viewPager.setCurrentItem(intent.getIntExtra("index", 0), false)
    }

    override fun initEvent() {

    }

    private inner class ImgAdapter : PagerAdapter() {
        override fun getCount(): Int {
            return mData.size
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = images[position.rem(4)]
            Glide.with(this@PicDetailsActivity)
                .load(mData[position].url + "@1080,1920.jpg")
                .apply(RequestOptions().centerCrop())
                .into(view)
            container.addView(view)
            view.setOnClickListener {

            }
            return view
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
//            super.destroyItem(container, position, `object`)
            val i = position % 4
            val view = images[i]
            container.removeView(view)
        }
    }
}