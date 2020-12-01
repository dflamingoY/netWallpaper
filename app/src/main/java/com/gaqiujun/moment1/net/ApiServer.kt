package com.gaqiujun.moment1.net

import com.gaqiujun.moment1.entity.BaseBean
import com.gaqiujun.moment1.entity.HomeData
import com.gaqiujun.moment1.entity.SearchTagBean
import com.mingo.baselibrary.net.procotol.BaseResp
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiServer {
    @GET("home.do")
    fun getHome(): Observable<BaseResp<HomeData>>

    @GET
    fun getData(@Url url: String): Observable<BaseResp<List<BaseBean>>>

    @GET("queryHotKey.do")
    fun getSearchTag(): Observable<BaseResp<List<SearchTagBean>>>

}