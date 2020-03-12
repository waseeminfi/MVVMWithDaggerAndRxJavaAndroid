package com.infi.mvvmwithrxjavademo.utils


import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.infi.mvvmwithrxjavademo.login.LoginViewModel
import com.infi.mvvmwithrxjavademo.login.Repository
import javax.inject.Inject


/**
 * Created by ${Saquib} on 03-05-2018.
 */
class ViewModelFactory @Inject constructor(repository: Repository) : ViewModelProvider.Factory {
    val repository: Repository
    @NonNull
    override fun <T : ViewModel?> create(@NonNull modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

    init {
        this.repository = repository
    }
}