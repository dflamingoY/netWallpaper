package com.gaqiujun.moment1.module

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

}