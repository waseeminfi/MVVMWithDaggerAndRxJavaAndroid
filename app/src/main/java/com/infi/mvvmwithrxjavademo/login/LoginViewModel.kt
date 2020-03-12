package com.infi.mvvmwithrxjavademo.login


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonElement
import com.infi.mvvmwithrxjavademo.LoginRequest
import com.infi.mvvmwithrxjavademo.utils.ApiResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody

/**
 * Created by ${Saquib} on 03-05-2018.
 */
class LoginViewModel(val repository: Repository) : ViewModel() {
    private val disposables = CompositeDisposable()
    private val responseLiveData = MutableLiveData<ApiResponse>()
    fun loginResponse(): MutableLiveData<ApiResponse> {
        return responseLiveData
    }

    /*
     * method to call normal login api with $(mobileNumber + password)
     * */
    fun hitLoginApi(loginRequest : LoginRequest) {
        disposables.add(repository.executeLogin(loginRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { d: Disposable? -> responseLiveData.setValue(ApiResponse.loading()) }
                .subscribe(
                        { result: JsonElement? -> responseLiveData.setValue(ApiResponse.success(result!!)) }
                ) { throwable: Throwable? -> responseLiveData.setValue(ApiResponse.error(throwable!!)) })
    }

    override fun onCleared() {
        disposables.clear()
    }

}