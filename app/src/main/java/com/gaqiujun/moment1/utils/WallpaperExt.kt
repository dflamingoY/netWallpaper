package com.gaqiujun.moment1.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.WallpaperManager
import android.graphics.Bitmap
import org.jetbrains.anko.displayMetrics
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException


@SuppressLint("MissingPermission")
fun Activity.applyWallpaper(bitmap: Bitmap) {
    val instance = WallpaperManager.getInstance(this);
    instance.suggestDesiredDimensions(displayMetrics.widthPixels, displayMetrics.heightPixels)
    try {
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
        instance.setStream(ByteArrayInputStream(bos.toByteArray()))
    } catch (e: IOException) {
    }
}
