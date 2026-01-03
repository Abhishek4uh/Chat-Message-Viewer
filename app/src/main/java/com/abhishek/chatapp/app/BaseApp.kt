package com.abhishek.chatapp.app

import android.app.Application
import com.abhishek.chatapp.common.Common.DEBUG_TAG
import com.abhishek.chatapp.di.CoreModule.provideIsDebug
import dagger.hilt.android.HiltAndroidApp
import io.mhssn.colorpicker.BuildConfig
import timber.log.Timber

@HiltAndroidApp
class BaseApp: Application() {
    override fun onCreate() {
        super.onCreate()
        if(provideIsDebug()){
            Timber.plant(Timber.DebugTree())
            Timber.tag(DEBUG_TAG).d("Timber is Initialized")
        }
    }
}