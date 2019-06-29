package com.app.shaditest.api

import com.app.shaditest.model.ResponseData
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("api/")
    fun getData(@Query("results") results: Int): Observable<Response<ResponseData>>

}