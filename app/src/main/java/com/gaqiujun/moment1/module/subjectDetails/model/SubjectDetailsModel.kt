package com.gaqiujun.moment1.module.subjectDetails.model

import com.gaqiujun.moment1.entity.SubjectDetailsData
import com.gaqiujun.moment1.net.ApiServer
import com.mingo.baselibrary.net.HttpServer
import com.mingo.baselibrary.net.procotol.BaseResp
import io.reactivex.Observable
import javax.inject.Inject

class SubjectDetailsModel @Inject constructor() {

    private val apiServer = HttpServer.getInstance().getApiServer(ApiServer::class.java)

    fun getSubjectData(id: String?): Observable<BaseResp<SubjectDetailsData>> {
        return apiServer.getSubjectDetails("specialDetail/$id.do")
    }

}