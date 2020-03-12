package com.infi.mvvmwithrxjavademo.utils

import com.google.gson.JsonElement
import com.infi.mvvmwithrxjavademo.LoginRequest
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by ${Saquib} on 03-05-2018.
 */
interface ApiCallInterface {
    @POST(Urls.LOGIN)
    fun login(@Body loginRequest: LoginRequest?): Observable<JsonElement?>
}