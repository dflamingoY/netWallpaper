package com.gaqiujun.moment1.module.sortSecond.model

import com.gaqiujun.moment1.entity.SortDataBody
import com.gaqiujun.moment1.net.ApiServer
import com.mingo.baselibrary.net.HttpServer
import com.mingo.baselibrary.net.procotol.BaseResp
import io.reactivex.Observable
import javax.inject.Inject

class SortDetailsModel @Inject constructor() {
    private val apiServer = HttpServer.getInstance().getApiServer(ApiServer::class.java)
    fun getSortDetails(id: String?): Observable<BaseResp<SortDataBody>> {
        return apiServer.getSortCat("showCatSub/$id/0/0.do")
    }

}