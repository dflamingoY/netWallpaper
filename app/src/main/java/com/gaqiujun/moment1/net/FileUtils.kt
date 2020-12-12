package com.gaqiujun.moment1.net

import android.app.Activity
import android.content.ContentValues
import android.os.Build
import android.provider.MediaStore
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.*

fun Activity.downPic(url: String, action: (String) -> Unit) {
    var suffix: String = url.substring(url.lastIndexOf("/") + 1, url.indexOf("@")) + ".jpg"
    GlobalScope.launch {
        val cacheFile = Glide.with(this@downPic.applicationContext)
            .applyDefaultRequestOptions(RequestOptions())
            .downloadOnly()
            .load(url)
            .submit()
            .get()
        val contentValues = ContentValues()
        contentValues.put(MediaStore.Downloads.DISPLAY_NAME, suffix)
        contentValues.put(MediaStore.Downloads.MIME_TYPE, "image/*")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentValues.put(MediaStore.Downloads.DATE_TAKEN, System.currentTimeMillis())
        }
        val uri1 = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentResolver
                .insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
        } else {
            contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
        }
        try {
            val os = contentResolver.openOutputStream(uri1!!)
            val inputStream = FileInputStream(cacheFile)
            val buffer = ByteArray(1024)
            var len: Int
            while (inputStream.read(buffer).also { len = it } > 0) {
                os?.write(buffer, 0, len)
                os?.flush()
            }
            os?.close()
            inputStream.close()
            action("保存成功")
        } catch (e: Exception) {
            action("保存失败")
        }
    }
}


private fun copyFile(resouseFile: File?, targetFile: File?) {
    val inputStream = FileInputStream(resouseFile)
    val outputStream = FileOutputStream(targetFile)
    val buffer = ByteArray(1024)
    var len: Int
    while (inputStream.read(buffer).also { len = it } > 0) {
        outputStream.write(buffer, 0, len)
        outputStream.flush()
    }
    outputStream.close()
    inputStream.close()
}