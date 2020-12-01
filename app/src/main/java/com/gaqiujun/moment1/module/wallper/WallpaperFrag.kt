package com.gaqiujun.moment1.module.wallper

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.gaqiujun.moment1.R
import com.gaqiujun.moment1.module.hot.HotFrag
import com.gaqiujun.moment1.module.selected.SelectedFrag
import com.gaqiujun.moment1.module.sort.SortFrag
import com.gaqiujun.moment1.module.subject.SubjectFrag
import com.mingo.baselibrary.base.BaseFragment
import kotlinx.android.synthetic.main.frag_wallpaper.*

class WallpaperFrag : BaseFragment() {
    private val fragments = arrayOf(SelectedFrag(), HotFrag(0), HotFrag(1), SubjectFrag(), SortFrag())
    private val titles = arrayOf("精选", "热门", "最新", "专题", "分类")

    override fun getLayoutId(): Int {
        return R.layout.frag_wallpaper
    }

    override fun initView() {

    }

    override fun initData() {
        viewPager.adapter = WallpaperAdapter(childFragmentManager)
        pagerSliding.setViewPager(viewPager)
    }

    override fun initEvent() {

    }

    private inner class WallpaperAdapter(fm: FragmentManager) :
        FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getCount(): Int {
            return 5
        }

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position]
        }
    }

}