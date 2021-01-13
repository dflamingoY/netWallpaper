package com.gaqiujun.moment1.module.privacy

import android.graphics.Bitmap
import android.net.http.SslError
import android.util.Log
import android.view.ViewTreeObserver
import android.view.animation.LinearInterpolator
import android.webkit.*
import com.gaqiujun.moment1.R
import com.gaqiujun.moment1.module.privacy.presenter.PrivacyPresenter
import com.mingo.baselibrary.base.BaseMvpAct
import com.mingo.baselibrary.utils.AppTools
import com.nineoldandroids.animation.ObjectAnimator
import kotlinx.android.synthetic.main.activity_privacy.*
import org.jsoup.Jsoup

class PrivacyActivity : BaseMvpAct<PrivacyPresenter>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_privacy
    }

    override fun initView() {

    }

    override fun initData() {
        initWebView()
    }

    private fun initWebView() {
        webView.settings.javaScriptEnabled = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.builtInZoomControls = false
        webView.settings.domStorageEnabled = true
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        webView.settings.setSupportZoom(false)
        webView.webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
            }
        }
        webView.webViewClient = object : WebViewClient() {
            override fun onReceivedSslError(
                view: WebView?,
                handler: SslErrorHandler?,
                error: SslError?
            ) {
                super.onReceivedSslError(view, handler, error)
            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                Log.d("Mozator", " 跳转  " + request!!.url.toString())
                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                view?.loadUrl("javascript:HTMLOUT.processHTML(document.documentElement.outerHTML);")
            }
        }
        webView.addJavascriptInterface(InJavaScriptLocalObj(), "HTMLOUT")
        webView.loadUrl("https://play.54647.site/game.html?id=39")
    }

    override fun initEvent() {
        ivDriver.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                ivDriver.viewTreeObserver.removeOnGlobalLayoutListener(this)
                //执行animated
                val anim = ObjectAnimator.ofFloat(
                    ivDriver,
                    "translationX",
                    -ivDriver.width.toFloat(),
                    AppTools.getWindowWidth(this@PrivacyActivity) + ivDriver.width.toFloat()
                ).setDuration(8000)
                anim.interpolator = LinearInterpolator()
                anim.repeatCount = ObjectAnimator.INFINITE
                anim.repeatMode = ObjectAnimator.RESTART
                anim.start()
            }
        })
    }

    inner class InJavaScriptLocalObj {
        @JavascriptInterface
        fun processHTML(html: String) {
            Log.d("Mozator", html)
            /**
             * 解析网页
             */
            /* val parse = Jsoup.parse(html)
             val vedioBox = parse.getElementById("videobox")
             val divs = vedioBox.select("div.listchannel")
             var isadd = false
             for (item in divs) {
                 val select = item.select("img").get(1)
                 val title = select.attr("title")
                 val src = select.attr("src")
                 if (TextUtils.isEmpty(title) && TextUtils.isEmpty(src))
                     continue
                 val payId = paserId(src)
                 if (TextUtils.isEmpty(payId) || "full" == payId) {
                     continue
                 }
                 isadd = true
             }*/
        }
    }

}