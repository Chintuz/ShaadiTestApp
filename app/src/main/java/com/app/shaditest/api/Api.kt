package com.app.shaditest.api

import com.app.shaditest.model.ResponseData
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET

interface Api {

    @GET("api/?results=10")
    fun getData(): Observable<Response<ResponseData>>

}