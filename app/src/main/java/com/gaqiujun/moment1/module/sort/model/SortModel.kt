package com.gaqiujun.moment1.module.sort.model

import com.gaqiujun.moment1.entity.BaseBean
import com.gaqiujun.moment1.net.ApiServer
import com.mingo.baselibrary.net.HttpServer
import com.mingo.baselibrary.net.procotol.BaseResp
import io.reactivex.Observable
import javax.inject.Inject

class SortModel @Inject constructor() {

    private val apiServer = HttpServer.getInstance().getApiServer(ApiServer::class.java)

    fun getSort(): Observable<BaseResp<List<BaseBean>>> {
        return apiServer.getData("showCatHome.do")
    }

}