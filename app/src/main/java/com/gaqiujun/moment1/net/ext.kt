package com.gaqiujun.moment1.net

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.View
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import com.gaqiujun.moment1.module.subjectDetails.SubjectDetailsActivity


fun Int.isNetOk() = this == 0

/**
 * 熱門  "pop/" + 0 + ".do"
 * 新 newest/0.do
 * 分類 showCatHome.do
 *专题  "specialList/" + 0 + ".do"
 * 标签 queryHotKey.do
 *
 * 搜索 "queryWallpaperByTid/" + mCurrentID + "/0.do
 *  //专题
 *   private int width = (int) (Tools.getWindowsWidth(getActivity()) /*- Tools.dip2px(getActivity(),10)*/);
private int height = (int) ((353 * 1.0f) / (730 * 1.0f) * width);
 * "@730,353.jpg"
 *
 *  "@305,300.jpg"
 *  "@1080,1920.jpg"
 *   "@400,400.jpg"
 *  "@200,355.jpg"
 *
 *     private int width = (int) (Tools.getWindowsWidth(getActivity()));
private int height = (int) ((338 * 1.0f) / (600 * 1.0f) * width);
 *
 * */

fun Activity.jump2Subject(id: String?, url: String, title: String?, view: View) {
    val intent =
        Intent(this, SubjectDetailsActivity::class.java)
            .putExtra("id", id)
            .putExtra("url", url)
            .putExtra("title", title)
    val options: ActivityOptionsCompat =
        ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, "anima")
    ActivityCompat.startActivity(this, intent, options.toBundle())
}

fun isM(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
}