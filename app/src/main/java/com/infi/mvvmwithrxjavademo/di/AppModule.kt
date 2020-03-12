package com.infi.mvvmwithrxjavademo.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by ${Saquib} on 03-05-2018.
 */
@Module
class AppModule(private val context: Context) {
    @Provides
    @Singleton
    fun provideContext(): Context {
        return context
    }

}