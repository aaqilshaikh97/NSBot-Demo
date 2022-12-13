package com.nsbot.demoapp.api

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.*

interface ApiInterface {

    @POST("accounts/sign-in/")
    fun signIn(@Body body: HashMap<String, Any>): Observable<ResponseBody>

    @GET("accounts/profile")
    fun loadProfile(): Observable<ResponseBody>

}