package com.gaqiujun.moment1.module.search.presenter

import com.gaqiujun.moment1.entity.BaseBean
import com.gaqiujun.moment1.entity.SearchTagBean
import com.gaqiujun.moment1.module.search.model.SearchModel
import com.gaqiujun.moment1.module.search.view.SearchWallpaperView
import com.gaqiujun.moment1.net.isNetOk
import com.mingo.baselibrary.extention.execute
import com.mingo.baselibrary.mvp.BasePresenter
import com.mingo.baselibrary.net.procotol.BaseResp
import com.mingo.baselibrary.rx.BaseObserver
import javax.inject.Inject

class SearchPresenter @Inject constructor() : BasePresenter<SearchWallpaperView>() {
    @Inject
    lateinit var model: SearchModel
    private var currentPage = 0
    fun getSearchTag() {
        model.searchTag().execute(object : BaseObserver<BaseResp<List<SearchTagBean>>>() {
            override fun onNext(t: BaseResp<List<SearchTagBean>>) {
                if (t.msgCode.isNetOk()) {
                    view.showTag(t.body)
                }
            }
        }, provider)
    }

    fun searchTag(tag: String) {
        model.queryTag(tag).execute(object : BaseObserver<BaseResp<List<SearchTagBean>>>() {
            override fun onNext(t: BaseResp<List<SearchTagBean>>) {
                if (t.msgCode.isNetOk()) {
                    view.searchResult(t.body)
                }
            }
        }, provider)
    }

    fun searchById(id: String?) {
        model.searchById(id, currentPage)
            .execute(object : BaseObserver<BaseResp<List<BaseBean>>>() {
                override fun onNext(t: BaseResp<List<BaseBean>>) {
                    if (t.msgCode.isNetOk()) {
                        view.showList(t.body)
                    }
                }
            }, provider)
    }

}