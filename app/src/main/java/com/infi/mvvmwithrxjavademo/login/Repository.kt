package com.infi.mvvmwithrxjavademo.login

import com.google.gson.JsonElement
import com.infi.mvvmwithrxjavademo.LoginRequest
import com.infi.mvvmwithrxjavademo.utils.ApiCallInterface
import io.reactivex.Observable
import okhttp3.ResponseBody

/**
 * Created by ${Waseem} on 03-05-2018.
 */
class Repository(private val apiCallInterface: ApiCallInterface) {
    /*
     * method to call login api
     * */
    fun executeLogin(loginRequest: LoginRequest?): Observable<JsonElement?> {
        return apiCallInterface.login(loginRequest)
    }

}