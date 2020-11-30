package com.mingo.baselibrary.utils

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.app.ActivityManager
import android.app.ActivityManager.RunningTaskInfo
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.text.TextUtils
import android.view.Gravity
import android.view.ViewConfiguration
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File
import java.util.*

class AppTools {

    companion object {
        const val TELEPHONE_REGEX = "[1][3456789]\\d{9}"
        fun getWindowWidth(context: Context): Int {
            return context.resources.displayMetrics.widthPixels
        }

        fun getWindowHeight(context: Context): Int {
            return context.resources.displayMetrics.heightPixels
        }

        fun dp2px(context: Context, value: Float): Float {
            return context.resources.displayMetrics.density * value
        }

        fun getVersion(activity: Context): String {
            val manager = activity.packageManager
            try {
                val info = manager.getPackageInfo(activity.packageName, 0)
                return info.versionName
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            return ""
        }

        @SuppressLint("ShowToast")
        fun openGallery(context: Activity, request: Int) {
            try {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                if (intent.resolveActivity(context.packageManager) != null) context.startActivityForResult(
                    intent,
                    request
                ) else {
                    Toast.makeText(context, "相册访问异常", Toast.LENGTH_SHORT)
                }
            } catch (e: Exception) {
            }
        }

        fun getPath(data: Uri?, activity: Activity): String? {
            try {
                val filePathColumn =
                    arrayOf(MediaStore.Images.Media.DATA)
                var cursor =
                    activity.contentResolver.query(data!!, filePathColumn, null, null, null)
                if (cursor != null) {
                    cursor.moveToFirst()
                    val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                    val picturePath = cursor.getString(columnIndex)
                    cursor.close()
                    cursor = null
                    if (picturePath == null || picturePath == "null") {
                        val toast = Toast.makeText(activity, "图片路径不合法", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                        return ""
                    }
                    return picturePath
                }
            } catch (e: java.lang.Exception) {
                return null
            }
            return null
        }

        @Throws(java.lang.Exception::class)
        fun openCamera(activity: Activity, code: Int, targetFile: File) {
            val intent = Intent("android.media.action.IMAGE_CAPTURE")
            // 判断存储卡是否可以用，可用进行存储
            targetFile.parentFile?.let {
                if (!it.exists()) {
                    it.mkdirs()
                }
            }
            val uri: Uri?
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProvider.getUriForFile(
                    activity,
                    activity.packageName + ".fileprovider",
                    targetFile
                )
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            } else {
                uri = Uri.fromFile(targetFile)
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            activity.startActivityForResult(intent, code)
        }

        fun createFile(context: Context): File {
            val rootFile =
                File(context.getExternalFilesDir(""), ".cache/image")
            if (!rootFile.exists()) {
                rootFile.mkdirs()
            }
            return File(rootFile, "${UUID.randomUUID()}.png")
        }

        //获取可清除删除的缓存地址
        fun getBootDir(context: Context): String {
            return File(context.getExternalFilesDir(""), ".cache").absolutePath
        }

        /**
         * 获取内部存储的文件路径
         */
        fun getExternalBoot(root: String? = null): File {
            val file = File(
                Environment.getExternalStorageDirectory(),
                "gaqiujun" + if (root != null) "/$root" else ""
            )
            if (!file.exists()) {
                file.mkdirs()
            }
            return file
        }

        fun isEmptyEt(editText: EditText, count: Int): Boolean {
            return if (count == 0) {
                TextUtils.isEmpty(editText.text.toString().trim())
            } else {
                if (TextUtils.isEmpty(
                        editText.text.toString().trim()
                    )
                ) true else {
                    editText.text.toString().trim().length < count
                }
            }
        }


        fun openVideo(activity: Activity, requestCode: Int, file: File) {
            val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            var uri: Uri?
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProvider.getUriForFile(
                    activity,
                    activity.packageName + ".fileprovider",
                    file
                )
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            } else {
                uri = Uri.fromFile(file)
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 120)
            intent.putExtra(
                MediaStore.EXTRA_VIDEO_QUALITY,
                0
            ); // set the video image quality to high
            // start the Video Capture Intent
            activity.startActivityForResult(intent, requestCode)
        }

        /**
         * 是否是前台进程
         *
         * @param var0
         * @return
         */
        fun isAppRunningForeground(var0: Context): Boolean {
            @SuppressLint("WrongConstant") val manager =
                var0.getSystemService("activity") as ActivityManager
            return try {
                val var2: List<*>? = manager.getRunningTasks(1)
                if (var2 != null && var2.isNotEmpty()) {
                    var0.packageName.equals(
                        (var2[0] as RunningTaskInfo).baseActivity?.packageName,
                        ignoreCase = true
                    )
                } else {
                    false
                }
            } catch (var4: SecurityException) {
                var4.printStackTrace()
                false
            }
        }

        fun getDensity(context: Context) = context.resources.displayMetrics.density

        fun getSuffix(path: String): String {
            return path.substring(path.lastIndexOf("/") + 1)
        }

        /**
         * 判断当前网络是否可用
         */
        @SuppressLint("MissingPermission")
        fun isNetOk(act: Context): Boolean {
            return try {
                val manager = act
                    .applicationContext.getSystemService(
                        Context.CONNECTIVITY_SERVICE
                    ) as ConnectivityManager
                val networkinfo = manager.activeNetworkInfo
                !(networkinfo == null || !networkinfo.isAvailable)
            } catch (e: java.lang.Exception) {
                false
            }
        }

        fun getStatusBarHeight(context: Context): Int {
            var statusBarHeight = 0
            val resourceId =
                context.resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                statusBarHeight = context.resources.getDimensionPixelSize(resourceId)
            }
            return statusBarHeight
        }

        fun getNavigationBarHeight(context: Context): Int {
            var result = 0
            if (AppTools.hasNavBar(context)) {
                val res = context.resources
                val resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android")
                if (resourceId > 0) {
                    result = res.getDimensionPixelSize(resourceId)
                }
            }
            return result
        }

        /**
         * 检查是否存在虚拟按键栏
         *
         * @param context
         * @return
         */
        @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
        fun hasNavBar(context: Context): Boolean {
            val res = context.resources
            val resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android")
            return if (resourceId != 0) {
                var hasNav = res.getBoolean(resourceId)
                // check override flag
                val sNavBarOverride: String? = getNavBarOverride()
                if ("1" == sNavBarOverride) {
                    hasNav = false
                } else if ("0" == sNavBarOverride) {
                    hasNav = true
                }
                hasNav
            } else { // fallback
                !ViewConfiguration.get(context).hasPermanentMenuKey()
            }
        }

        /**
         * 判断虚拟按键栏是否重写
         *
         * @return
         */
        private fun getNavBarOverride(): String? {
            var sNavBarOverride: String? = null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                try {
                    val c =
                        Class.forName("android.os.SystemProperties")
                    val m =
                        c.getDeclaredMethod("get", String::class.java)
                    m.isAccessible = true
                    sNavBarOverride = m.invoke(null, "qemu.hw.mainkeys") as String
                } catch (e: Throwable) {
                }
            }
            return sNavBarOverride
        }

        //解析图片的宽高
        fun decodeImgInfo(path: String): String {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFile(
                path,
                options
            )
            val imgWidth: Int = options.outWidth
            val imgHeight: Int = options.outHeight
            return "{\"imgParams\":{\"width\":$imgWidth,\"height\":$imgHeight}}"
        }

        @SuppressLint("MissingPermission")
        fun getIdentity(context: Context): String {
            var identity: String = getAndroidID(context)
            if (TextUtils.isEmpty(identity)) {
                identity = getDeviceID()
            }
            return identity
        }

        @SuppressLint("HardwareIds")
        fun getAndroidID(context: Context): String {
            val id = Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            )
            return id ?: ""
        }

        private fun getDeviceID(): String {
            var serial: String?
            val szDevIDShort =
                "35" + Build.BOARD.length % 10 + Build.BRAND.length % 10 + Build.CPU_ABI.length % 10 + Build.DEVICE.length % 10 + Build.DISPLAY.length % 10 + Build.HOST.length % 10 + Build.ID.length % 10 + Build.MANUFACTURER.length % 10 + Build.MODEL.length % 10 + Build.PRODUCT.length % 10 + Build.TAGS.length % 10 + Build.TYPE.length % 10 + Build.USER.length % 10 //13 位
            try {
                serial = Build::class.java.getField("SERIAL").toString()
                //API>=9 使用serial号
                return UUID(
                    szDevIDShort.hashCode().toLong(),
                    serial.hashCode().toLong()
                ).toString()
            } catch (exception: Exception) {
                serial = "serial"
            }
            return UUID(szDevIDShort.hashCode().toLong(), serial.hashCode().toLong()).toString()
        }

    }
}