package com.mingo.baselibrary.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.mingo.baselibrary.R
import com.mingo.baselibrary.utils.AppTools

class CustomToolBarView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    init {
        val array =
            context.obtainStyledAttributes(attrs, R.styleable.CustomToolBarView)
        val title = array.getString(R.styleable.CustomToolBarView_toolbarTitle)
        val size = array.getFloat(R.styleable.CustomToolBarView_toolbarSize, 14f)
        array.recycle()
        val tv = TextView(context)
        tv.setTextColor(ContextCompat.getColor(context, R.color.colorWhite))
        tv.textSize = 17f
        tv.text = title
        tv.textSize = size
        tv.gravity = Gravity.CENTER
        val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        params.addRule(CENTER_IN_PARENT)
        addView(tv, params)
        val iv = ImageView(context)
        val ivParams =
            LayoutParams(AppTools.dp2px(context, 30f).toInt(), AppTools.dp2px(context, 47f).toInt())
        iv.setOnClickListener {
            onClickListener?.onClick(it)
        }
        val ivPadding = AppTools.dp2px(context, 10f).toInt()
        iv.setPadding(ivPadding, ivPadding, ivPadding, ivPadding)
        iv.setImageResource(R.mipmap.icon_back)
        ivParams.addRule(CENTER_VERTICAL)
        addView(iv, ivParams)
    }

    private var onClickListener: OnClickListener? = null

    override fun setOnClickListener(l: OnClickListener?) {
        this.onClickListener = l
    }
}