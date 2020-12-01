package com.gaqiujun.moment1.module

import android.content.Intent
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import com.gaqiujun.moment1.R
import com.gaqiujun.moment1.module.search.SearchFrag
import com.gaqiujun.moment1.module.wallper.WallpaperFrag
import com.mingo.baselibrary.base.BaseSuperAct
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseSuperAct() {

    private val wallpaperFrag = WallpaperFrag()
    private val searchFrag = SearchFrag()
    private var currentFrag: Fragment? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        setTransparentStatusBar()
        switchFragment(0)
    }

    override fun initData() {

    }

    override fun initEvent() {
        tvWallpaper.setOnClickListener {
            switchFragment(0)
        }
        tvSearch.setOnClickListener {
            switchFragment(1)
        }
    }

    @Synchronized
    private fun switchFragment(type: Int) {
        val transTemp = supportFragmentManager.beginTransaction()
        currentFrag?.let { transTemp.hide(currentFrag!!) }
        currentFrag = when (type) {
            0 -> {
                wallpaperFrag
            }
            else -> {
                searchFrag
            }
        }
        currentFrag?.let {
            if (!currentFrag!!.isAdded) {
                transTemp.add(R.id.frameContainer, currentFrag!!)
            }
        }
        transTemp.show(currentFrag!!)
        transTemp.commitAllowingStateLoss()
        supportFragmentManager.executePendingTransactions()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK// 注意
            intent.addCategory(Intent.CATEGORY_HOME)
            this.startActivity(intent)
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}