package com.gaqiujun.moment1.widget

import android.content.Context
import android.util.AttributeSet
import com.bumptech.glide.Glide
import com.gaqiujun.moment1.R
import com.mingo.baselibrary.widget.BaseLayout
import kotlinx.android.synthetic.main.layout_round_recommend.view.*

class CircleCardImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseLayout(context, attrs, defStyleAttr) {
    override fun getLayoutId(): Int {
        return R.layout.layout_round_recommend
    }

    fun showPic(url: String) {
        Glide.with(context)
            .load(url)
            .into(ivCenter)
    }

}