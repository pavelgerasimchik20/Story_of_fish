package com.geras.fishistory

import android.app.Application
import com.geras.fishistory.di.AppComponent
import com.geras.fishistory.di.DaggerAppComponent

class FishHistoryApplication : Application() {

    private var _appComponent: AppComponent? = null
    val appComponent: AppComponent get() = _appComponent!!

    override fun onCreate() {
        super.onCreate()
        _appComponent = DaggerAppComponent.builder().application(this).build()
    }
}