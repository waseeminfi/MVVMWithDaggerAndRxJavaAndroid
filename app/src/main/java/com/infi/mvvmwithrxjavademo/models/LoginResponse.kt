package com.infi.mvvmwithrxjavademo.models

data class LoginResponse(
        val message: String,
        val result: String,
        val token: String,
        val user: User
)

data class User(
        val cid: String,
        val logo: Any,
        val name: String,
        val rid: String,
        val type: Any
)