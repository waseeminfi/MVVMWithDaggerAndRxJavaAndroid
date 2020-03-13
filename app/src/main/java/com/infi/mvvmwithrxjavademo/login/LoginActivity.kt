package com.infi.mvvmwithrxjavademo.login

import android.app.ProgressDialog

import android.os.Bundle

import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.infi.mvvmwithrxjavademo.*
import com.infi.mvvmwithrxjavademo.models.LoginResponse
import com.infi.mvvmwithrxjavademo.utils.ApiResponse
import com.infi.mvvmwithrxjavademo.utils.Constant
import com.infi.mvvmwithrxjavademo.utils.Status
import com.infi.mvvmwithrxjavademo.utils.ViewModelFactory
import com.izikode.izilib.roguin.endpoint.FacebookEndpoint
import com.izikode.izilib.roguin.endpoint.GoogleEndpoint
import com.izikode.izilib.roguin.endpoint.TwitterEndpoint
import com.izikode.izilib.roguin.helper.RoguinActivity
import javax.inject.Inject

/**
 * Created by ${Waseem} on 03-05-2018.
 */
class LoginActivity : BaseLoginActivity()     {
    @set:Inject
    var viewModelFactory: ViewModelFactory? = null
    @BindView(R.id.phone_no)
    lateinit var phoneNo: EditText
    @BindView(R.id.password)
    lateinit  var password: EditText

    @BindView(R.id.google_button)
    lateinit var google_btn : Button

    @BindView(R.id.facebook_button)
    lateinit var facebook_btn : Button

    var viewModel: LoginViewModel? = null
    var progressDialog: ProgressDialog? = null


    private val googleEndpoint by lazy { GoogleEndpoint(this) }
    private val facebookEndpoint by lazy { FacebookEndpoint(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        ButterKnife.bind(this)
        (application as MyApplication).appComponent!!.doInjection(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)
        viewModel!!.loginResponse().observe(this, Observer { apiResponse: ApiResponse? -> consumeResponse(apiResponse) })
    }


    @OnClick(R.id.login)
    fun onLoginClicked() {
        if (isValid) {
            if (!Constant.checkInternetConnection(this)) {
                Toast.makeText(this@LoginActivity, resources.getString(R.string.network_error), Toast.LENGTH_SHORT).show()
            } else {
                viewModel!!.hitLoginApi(LoginRequest(phoneNo!!.text.toString(), password!!.text.toString()))
            }
        }
    }

    @OnClick(R.id.google_button)
    fun onGoogleLogin(){
        if (googleEndpoint.isSignedIn) {
            googleEndpoint.requestSignOut { success ->
                if (success) {
                   Toast.makeText(this,"Google is Disconnected",Toast.LENGTH_LONG).show()
                }
            }
        } else {
            googleEndpoint.requestSignIn { success, token, error ->
                if (success) {
                    Toast.makeText(this,"Google is Connected",Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    @OnClick(R.id.facebook_button)
    fun onFaceBookLogin(){
        if (facebookEndpoint.isSignedIn) {
            facebookEndpoint.requestSignOut { success ->
                if (success) {
                    Toast.makeText(this,"Google is Connected",Toast.LENGTH_LONG).show()
                }
            }
        } else {
            facebookEndpoint.requestSignIn { success, token, error ->
                if (success) {

                    Log.d("Facebook TOKEN", token.toString())
                }
            }
        }

    }

    fun getProfileInfo(){
        arrayOf(googleEndpoint, facebookEndpoint).forEach { endpoint ->
            if (endpoint.isSignedIn) {
                endpoint.requestProfile { success, profile, error ->
                    if (success) {
                        Log.d("RoguinEndpoint", endpoint.toString())
                        Log.d("RoguinProfile", profile.toString())
                    }
                }
            }
        }
    }


    /*
     * method to validate $(mobile number) and $(password)
     * */
    private val isValid: Boolean
        private get() {
            if (phoneNo!!.text.toString().trim { it <= ' ' }.isEmpty()) {
                Toast.makeText(this@LoginActivity, resources.getString(R.string.enter_valid_mobile), Toast.LENGTH_SHORT).show()
                return false
            } else if (password!!.text.toString().trim { it <= ' ' }.isEmpty()) {
                Toast.makeText(this@LoginActivity, resources.getString(R.string.enter_valid_password), Toast.LENGTH_SHORT).show()
                return false
            }
            return true
        }

    /*
     * method to handle response
     * */
    private fun consumeResponse(apiResponse: ApiResponse?) {
        when (apiResponse!!.status) {
            Status.LOADING -> showProgressDialog()
            Status.SUCCESS -> {
               dismissProgressDialog()
                renderSuccessResponse(apiResponse.data)
            }
            Status.ERROR -> {
               dismissProgressDialog()
                Toast.makeText(this@LoginActivity, resources.getString(R.string.errorString), Toast.LENGTH_SHORT).show()
            }
            else -> {
            }
        }
    }

    /*
    * method to handle success response
    * */
    private fun renderSuccessResponse(response: JsonElement?) = if (response !=null) {
        val loginResponsetype = object : TypeToken<LoginResponse>() {}.type
        var loginResponse = Constant.parseJsonElementToObject(response,loginResponsetype)
    } else {
        Toast.makeText(this@LoginActivity, resources.getString(R.string.errorString), Toast.LENGTH_SHORT).show()
    }
}