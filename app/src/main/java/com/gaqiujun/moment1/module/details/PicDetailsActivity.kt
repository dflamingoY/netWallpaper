package com.gaqiujun.moment1.module.details

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.gaqiujun.moment1.R
import com.gaqiujun.moment1.entity.BaseBean
import com.gaqiujun.moment1.module.details.presenter.PicDetailPresenter
import com.gaqiujun.moment1.module.details.view.PicDetailsView
import com.gaqiujun.moment1.net.downPic
import com.gaqiujun.moment1.net.isM
import com.gaqiujun.moment1.utils.applyWallpaper
import com.mingo.baselibrary.base.BaseMvpAct
import com.mingo.baselibrary.utils.AppTools
import com.mingo.baselibrary.utils.LogUtils
import com.nineoldandroids.animation.Animator
import com.nineoldandroids.animation.AnimatorListenerAdapter
import com.nineoldandroids.animation.ObjectAnimator
import kotlinx.android.synthetic.main.activity_pic_details.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class PicDetailsActivity : BaseMvpAct<PicDetailPresenter>(), PicDetailsView {
    private val mData by lazy { ArrayList<BaseBean>() }
    private val images by lazy { images() }

    companion object {
        private const val REQUEST_PERMISSION_EXTERNAL = 0x00
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_pic_details
    }

    private fun images() = Array(4) {
        LayoutInflater.from(this).inflate(R.layout.layout_img, null, false)
    }

    override fun initView() {
        setTransparentStatusBar()
    }

    override fun initData() {
        (intent.getSerializableExtra("data") as ArrayList<BaseBean>?)?.let {
            mData.addAll(it)
        }
        viewPager.adapter = ImgAdapter()
        viewPager.setCurrentItem(intent.getIntExtra("index", 0), false)
    }

    override fun initEvent() {
        ivApply.setOnClickListener {
            ObjectAnimator.ofFloat(it, "rotation", 0f, 360f).setDuration(1000).start()
            Glide.with(this)
                .asBitmap()
                .load("${mData[viewPager.currentItem].url}@1080,1920.jpg")
                .listener(object : RequestListener<Bitmap> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Bitmap>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return true
                    }

                    override fun onResourceReady(
                        resource: Bitmap?,
                        model: Any?,
                        target: Target<Bitmap>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        resource?.let {
                            applyWallpaper(it)
                            showToast("应用成功")
                        }
                        return true
                    }
                }).submit()
        }
        ivDownload.setOnClickListener {
            //请求权限
            ObjectAnimator.ofFloat(it, "translationY", AppTools.dp2px(this, 10f), 0f)
                .setDuration(520).apply {
                    interpolator = BounceInterpolator()
                }.start()
            if (isM()) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        REQUEST_PERMISSION_EXTERNAL
                    )
                } else {
                    downPic("${mData[viewPager.currentItem].url}@1080,1920.jpg")
                }
            } else {
                downPic("${mData[viewPager.currentItem].url}@1080,1920.jpg")
            }
        }
    }

    private var isAnimated = false

    /**
     * 动画
     */
    @SuppressLint("Recycle")
    private fun animated() {
        LogUtils.d("Mozator", "${ivApply.y}")
        if (!isAnimated) {
            isAnimated = true
            ObjectAnimator.ofFloat(
                ivApply,
                "translationY",
                if (ivApply.y < AppTools.getWindowHeight(this)) AppTools.dp2px(this, 61f) else 0f
            ).setDuration(220).start()
            ObjectAnimator.ofFloat(
                ivDownload,
                "translationY",
                if (ivApply.y < AppTools.getWindowHeight(this)) AppTools.dp2px(this, 61f) else 0f
            ).setDuration(220).apply {
                startDelay = 50
                this.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        isAnimated = false
                    }
                })
            }.start()
        }
    }

    private inner class ImgAdapter : PagerAdapter() {
        override fun getCount(): Int {
            return mData.size
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = images[position.rem(4)]
            Glide.with(this@PicDetailsActivity)
                .load(mData[position].url + "@1080,1920.jpg")
                .apply(RequestOptions().centerCrop())
                .into(view.findViewById(R.id.iv_img))
            container.addView(view)
            view.setOnClickListener {
                animated()
            }
            return view
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            val i = position % 4
            val view = images[i]
            container.removeView(view)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_EXTERNAL) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                downPic("${mData[viewPager.currentItem].url}@1080,1920.jpg")
            }
        }
    }

    private fun downPic(uri: String) {
        downPic(uri) {
            runBlocking {
                launch(Dispatchers.Main) {
                    showToast(it)
                }
            }
        }
    }

}