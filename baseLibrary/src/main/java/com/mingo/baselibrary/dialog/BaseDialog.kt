package com.mingo.baselibrary.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window

abstract class BaseDialog : Dialog {
    constructor(context: Context) : super(context)
    constructor(context: Context, themeId: Int) : super(context, themeId)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(getLayoutId())
        initView()
    }

    abstract fun getLayoutId(): Int
    abstract fun initView()

    //底部弹出
    protected open fun initSystem() {
        val p = window?.attributes
        p?.gravity = Gravity.BOTTOM
        p?.width = context.resources.displayMetrics.widthPixels
        window?.attributes = p
    }

    //中间展示铺满横屏
    protected open fun fillWidth() {
        window?.setBackgroundDrawable(ColorDrawable(0))
        val manager = window?.windowManager
        val attributes = window?.attributes
        val display = manager?.defaultDisplay
        attributes?.width = display?.width
        window?.attributes = attributes
    }

    protected var onClickListener: View.OnClickListener? = null

    open fun setOnClickListener(onClickListener: View.OnClickListener): BaseDialog {
        this.onClickListener = onClickListener
        return this
    }
}