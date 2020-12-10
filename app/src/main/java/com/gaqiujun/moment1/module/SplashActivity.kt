package com.gaqiujun.moment1.module

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import android.view.ViewTreeObserver
import com.gaqiujun.moment1.R
import com.mingo.baselibrary.base.BaseBindAct
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.startActivity

class SplashActivity : BaseBindAct() {
    override fun dataBind() {
        setContentView(R.layout.activity_splash)
    }

    override fun initView() {
    }

    override fun initData() {
    }

    override fun initEvent() {
        if (!this.isTaskRoot) {
            val intent = intent
            if (intent != null) {
                val action = intent.action
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN == action) {
                    finish()
                    return
                }
            }
        }
        frameSplash.viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                frameSplash.viewTreeObserver.removeOnGlobalLayoutListener(this)
                frameSplash.setBackgroundColor(Color.WHITE)
                ObjectAnimator.ofFloat(ivLogo, "scaleX", 0.9f, 1f).setDuration(320).start()
                ObjectAnimator.ofFloat(ivLogo, "scaleY", 0.9f, 1f).setDuration(320).start()

                val anim = ObjectAnimator.ofFloat(
                        ivLogo,
                        "alpha",
                        0.5f, 1f
                ).setDuration(320)
                anim.start()
                anim.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        startActivity<MainActivity>()
                        overridePendingTransition(0, 0)
                        finish()
                    }
                })
            }
        })

    }
}