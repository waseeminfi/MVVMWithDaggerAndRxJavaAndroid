package com.infi.mvvmwithrxjavademo.di

import com.infi.mvvmwithrxjavademo.login.LoginActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by ${Saquib} on 03-05-2018.
 */
@Component(modules = [AppModule::class, UtilsModule::class])
@Singleton
interface AppComponent {
    fun doInjection(loginActivity: LoginActivity)
}