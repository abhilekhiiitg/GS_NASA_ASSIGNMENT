package com.gs.nasa

import android.app.Application
import com.gs.nasa.pod.di.component.AppComponent
import com.gs.nasa.pod.di.component.DaggerAppComponent

class NasaPotdApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appContext(this)
            .build()
    }
}