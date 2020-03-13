package com.infi.mvvmwithrxjavademo

import android.os.Bundle
import com.infi.mvvmwithrxjavademo.custome.CustomProgressBar
import com.izikode.izilib.roguin.helper.RoguinActivity

abstract class BaseLoginActivity : RoguinActivity() {

    var progressBar = CustomProgressBar()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    fun showProgressDialog() {
        progressBar.show(this,"Please Wait ..")
    }

    fun dismissProgressDialog() {
        progressBar.dialog.dismiss()
    }
}