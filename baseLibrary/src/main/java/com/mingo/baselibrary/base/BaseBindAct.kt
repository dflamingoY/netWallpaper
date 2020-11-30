package com.mingo.baselibrary.base

import android.os.Bundle
import android.view.MenuItem
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity

abstract class BaseBindAct : RxAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBind()
        initView()
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initData()
        initEvent()
    }

    abstract fun dataBind()

    abstract fun initView()

    abstract fun initData()

    abstract fun initEvent()

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}