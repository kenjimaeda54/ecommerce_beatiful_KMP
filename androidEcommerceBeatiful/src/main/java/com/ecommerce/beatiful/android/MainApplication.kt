package com.ecommerce.beatiful.android

import android.app.Application
import com.ecommerce.beatiful.di.initKoin
import org.koin.android.ext.koin.androidContext

class MainApplication: Application() {


    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@MainApplication)
        }
    }

}