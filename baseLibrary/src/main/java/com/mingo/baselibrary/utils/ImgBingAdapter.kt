package com.mingo.baselibrary.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

class ImgBingAdapter {

    companion object {
        @JvmStatic
        @BindingAdapter("app:imageUrl", requireAll = false)
        fun loadAvatar(img: ImageView, url: String) {
            Glide.with(img.context)
                .load(url)
                .into(img)
        }
    }

}