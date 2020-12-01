package com.gaqiujun.moment1.module.hot.model

import com.gaqiujun.moment1.entity.BaseBean
import com.gaqiujun.moment1.net.ApiServer
import com.mingo.baselibrary.net.HttpServer
import com.mingo.baselibrary.net.procotol.BaseResp
import io.reactivex.Observable
import javax.inject.Inject

class HotModel @Inject constructor() {
    private val apiServer = HttpServer.getInstance().getApiServer(ApiServer::class.java)

    fun getList(url: String): Observable<BaseResp<List<BaseBean>>> {
        return apiServer.getData(url)
    }

}