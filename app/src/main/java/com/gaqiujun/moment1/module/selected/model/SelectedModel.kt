package com.gaqiujun.moment1.module.selected.model

import com.gaqiujun.moment1.entity.HomeData
import com.gaqiujun.moment1.net.ApiServer
import com.mingo.baselibrary.net.HttpServer
import com.mingo.baselibrary.net.procotol.BaseResp
import io.reactivex.Observable
import javax.inject.Inject

class SelectedModel @Inject constructor() {
    private val apiServer = HttpServer.getInstance().getApiServer(ApiServer::class.java)

    fun getHomeData(): Observable<BaseResp<HomeData>> {
        return apiServer.getHome()
    }
}