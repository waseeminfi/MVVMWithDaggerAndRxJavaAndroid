package com.infi.mvvmwithrxjavademo

import android.app.Application
import android.content.Context
import com.infi.mvvmwithrxjavademo.di.AppComponent
import com.infi.mvvmwithrxjavademo.di.AppModule
import com.infi.mvvmwithrxjavademo.di.DaggerAppComponent

import com.infi.mvvmwithrxjavademo.di.UtilsModule
import timber.log.Timber

/**
 * Created by ${Waseem} on 03-05-2018.
 */
class MyApplication : Application() {
    var appComponent: AppComponent? = null
   private var context: Context? = null
    override fun onCreate() {
        super.onCreate()
        context = this
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).utilsModule(UtilsModule()).build();

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

    }

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(context)
    }
}