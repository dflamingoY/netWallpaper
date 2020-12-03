package com.gaqiujun.moment1.module.search.model

import com.gaqiujun.moment1.entity.BaseBean
import com.gaqiujun.moment1.entity.SearchTagBean
import com.gaqiujun.moment1.net.ApiServer
import com.mingo.baselibrary.net.HttpServer
import com.mingo.baselibrary.net.procotol.BaseResp
import io.reactivex.Observable
import javax.inject.Inject

class SearchModel @Inject constructor() {
    private val apiServer = HttpServer.getInstance().getApiServer(ApiServer::class.java)

    fun searchTag(): Observable<BaseResp<List<SearchTagBean>>> {
        return apiServer.getSearchTag()
    }

    fun searchById(id: String?, page: Int): Observable<BaseResp<List<BaseBean>>> {
        return apiServer.getData("queryWallpaperByTid/$id/$page.do")
    }

    fun queryTag(tag: String): Observable<BaseResp<List<SearchTagBean>>> {
        return apiServer.getQueryTag(HashMap<String, String>().apply {
            put("k", tag)
            put("p", "0")
        })
    }

}