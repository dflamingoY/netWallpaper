package com.gaqiujun.moment1.module.search

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.gaqiujun.moment1.R
import com.gaqiujun.moment1.adapter.BaseAdapterHelper
import com.gaqiujun.moment1.adapter.QuickAdapter
import com.gaqiujun.moment1.entity.BaseBean
import com.gaqiujun.moment1.entity.SearchTagBean
import com.gaqiujun.moment1.injection.component.DaggerSearchActComponent
import com.gaqiujun.moment1.module.details.PicDetailsActivity
import com.gaqiujun.moment1.module.search.presenter.SearchPresenter
import com.gaqiujun.moment1.module.search.view.SearchWallpaperView
import com.gaqiujun.moment1.utils.SoftkeyBoardManager
import com.gaqiujun.moment1.widget.dTag.TagsAdapter
import com.mingo.baselibrary.base.BaseMvpAct
import com.mingo.baselibrary.utils.AppTools
import com.nineoldandroids.animation.ObjectAnimator
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.startActivity

class SearchActivity : BaseMvpAct<SearchPresenter>(), SearchWallpaperView {
    private lateinit var tagAdapter: TagsAdapter
    private val mData by lazy { ArrayList<SearchTagBean>() }
    private val searchData by lazy { ArrayList<BaseBean>() }
    private lateinit var adapter: QuickAdapter<BaseBean>
    private val tagData by lazy { ArrayList<SearchTagBean>() }
    private lateinit var nameAdapter: QuickAdapter<SearchTagBean>

    private lateinit var headView: View
    override fun getLayoutId(): Int {
        return R.layout.activity_search
    }

    override fun initView() {
        setTransparentStatusBar()
        DaggerSearchActComponent.builder().activityComponent(actComponent).build().inject(this)
        presenter.view = this
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        headView =
            LayoutInflater.from(this).inflate(R.layout.layout_head_search, recyclerView, false)
    }

    override fun initData() {
        tagAdapter = object : TagsAdapter() {
            override fun getCount(): Int {
                return mData.size
            }

            override fun getView(context: Context?, position: Int, parent: ViewGroup?): View {
                val tv = TextView(this@SearchActivity)
                tv.text = mData[position].title
                tv.textSize = 10f
                val mGd = GradientDrawable()
                if (!TextUtils.isEmpty(mData[position].color)) {
                    val colors = mData[position].color?.split(",")
                    mGd.setColor(
                        Color.parseColor(
                            "#" + Integer.toHexString(
                                colors!![0].toInt()
                            ) + Integer.toHexString(colors[1].toInt()) + Integer.toHexString(
                                colors[2].toInt()
                            )
                        )
                    )
                } else {
                    mGd.setColor(Color.WHITE)
                }
                tv.setPadding(10, 5, 10, 5)
                mGd.cornerRadius = 10.0f
                tv.background = mGd
                return tv
            }

            override fun getItem(position: Int): Any {
                return mData[position]
            }

            override fun getPopularity(position: Int): Int {
                return 2
            }

            override fun onThemeColorChanged(view: View?, themeColor: Int, alpha: Float) {
            }
        }
        tagCloudView.setAdapter(tagAdapter)
        tagCloudView.setManualScroll(false)
        adapter = object : QuickAdapter<BaseBean>(this, R.layout.item_img, searchData, headView) {
            val width = AppTools.getWindowWidth(this@SearchActivity) / 3
            val height: Int = ((355f / 200f * width + 0.5f).toInt())
            val params = RelativeLayout.LayoutParams(width, height)
            override fun convert(helper: BaseAdapterHelper?, item: BaseBean?) {
                Glide.with(this@SearchActivity)
                    .load(item!!.url + "@200,355.jpg")
                    .into(helper!!.getImageView(R.id.iv_img))
                helper.getImageView(R.id.iv_img).layoutParams = params
            }
        }
        recyclerView.adapter = adapter
        (recyclerView.layoutManager as GridLayoutManager).spanSizeLookup = object :
            GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (adapter.isHeader(position)) 3 else 1
            }
        }
        nameAdapter =
            object : QuickAdapter<SearchTagBean>(this, R.layout.item_search_name, tagData) {
                override fun convert(helper: BaseAdapterHelper?, item: SearchTagBean?) {
                    helper!!.getTextView(R.id.tvName).text = item!!.title
                }
            }
        recyclerTag.layoutManager = LinearLayoutManager(this)
        recyclerTag.adapter = nameAdapter
        presenter.getSearchTag()
    }

    override fun initEvent() {
        adapter.setOnItemClickListener { _, position ->
            startActivity<PicDetailsActivity>("data" to searchData, "index" to position)
        }
        etContent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                tagData.clear()
                nameAdapter.notifyDataSetChanged()
                presenter.searchTag(s.toString().trim())
            }
        })
        nameAdapter.setOnItemClickListener { _, position ->
            linearResult.visibility = View.GONE
            presenter.searchById(tagData[position].id)
            hideSoft()
        }
        tvCancel.setOnClickListener {
            linearResult.visibility = View.GONE
            searchData.clear()
            adapter.notifyDataSetChanged()
            hideSoft()
            etContent.setText("")
            tvCancel.visibility = View.GONE
        }

        SoftkeyBoardManager(window.decorView, false).addSoftKeyboardStateListener(object :
            SoftkeyBoardManager.SoftKeyboardStateListener {
            override fun onSoftKeyboardOpened(keyboardHeightInPx: Int) {
                tvCancel.visibility = View.VISIBLE
            }

            override fun onSoftKeyboardClosed() {

            }
        })

    }

    private fun hideSoft() {
        (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
            etContent.windowToken,
            0
        )
    }

    override fun showTag(list: List<SearchTagBean>?) {
        list?.let {
            mData.addAll(it)
            tagAdapter.notifyDataSetChanged()
        }
    }

    override fun showList(list: List<BaseBean>?) {
        list?.let {
            searchData.addAll(it)
            adapter.notifyDataSetChanged()
        }
    }

    override fun searchResult(list: List<SearchTagBean>?) {
        list?.let {
            if (linearResult.visibility != View.VISIBLE) {
                linearResult.visibility = View.VISIBLE
                ObjectAnimator.ofFloat(
                    linearResult,
                    "translationY",
                    -AppTools.dp2px(this, 300f),
                    0f
                ).setDuration(320).start()
            }
            tagData.addAll(it)
            nameAdapter.notifyDataSetChanged()
        }
    }

}