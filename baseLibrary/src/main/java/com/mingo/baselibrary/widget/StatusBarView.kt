package com.mingo.baselibrary.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.mingo.baselibrary.utils.AppTools

class StatusBarView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(
            MeasureSpec.getSize(widthMeasureSpec),
            measureHeight(heightMeasureSpec)
        )
    }

    private fun measureHeight(measureSpec: Int): Int {
        val mode = MeasureSpec.getMode(measureSpec)
        val size = MeasureSpec.getSize(measureSpec)
        return if (mode == MeasureSpec.EXACTLY) {
            size
        } else {
            AppTools.getStatusBarHeight(context)
        }
    }
}