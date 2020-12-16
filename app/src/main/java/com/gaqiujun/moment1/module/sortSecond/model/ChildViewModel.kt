package com.gaqiujun.moment1.module.sortSecond.model

import com.gaqiujun.moment1.entity.SortDataBody
import com.gaqiujun.moment1.net.ApiServer
import com.mingo.baselibrary.net.HttpServer
import com.mingo.baselibrary.net.procotol.BaseResp
import io.reactivex.Observable

class ChildViewModel {

    private val apiServer = HttpServer.getInstance().getApiServer(ApiServer::class.java)

    fun getChildList(id: String?, index: Int, type: Int): Observable<BaseResp<SortDataBody>> {
        return apiServer.getSortCat("showCatSub/$id/$type/$index.do")
    }

    fun getCatList(id: String?): Observable<BaseResp<SortDataBody>> {
        return apiServer.getSortCat("showCatSub/$id/0/1.do")
    }
}