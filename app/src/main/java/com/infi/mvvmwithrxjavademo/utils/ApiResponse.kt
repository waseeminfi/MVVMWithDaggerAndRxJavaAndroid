package com.infi.mvvmwithrxjavademo.utils

import com.google.gson.JsonElement


/**
 * Created by ${Saquib} on 03-05-2018.
 */
class ApiResponse private constructor(val status: Status, val data: JsonElement?, val error: Throwable?) {

    companion object {
        fun loading(): ApiResponse {
            return ApiResponse(Status.LOADING, null, null)
        }

        fun success(data: JsonElement): ApiResponse {
            return ApiResponse(Status.SUCCESS, data, null)
        }

        fun error(error: Throwable): ApiResponse {
            return ApiResponse(Status.ERROR, null, error)
        }
    }

}