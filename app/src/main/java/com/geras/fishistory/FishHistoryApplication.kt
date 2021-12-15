package com.geras.fishistory

import android.app.Application
import com.geras.fishistory.di.AppComponent
import com.geras.fishistory.di.DaggerAppComponent
import javax.inject.Inject

class FishHistoryApplication: Application() {

    private var _appComponent: AppComponent? = null
    val appComponent: AppComponent get() = _appComponent!!

    override fun onCreate() {
        super.onCreate()
        _appComponent = DaggerAppComponent.builder().application(this).build()
    }
}

//abstract class AppModule {
//
//    @Binds
//    @IntoMap
//    fun provideDatabase(): FishRoomDatabase
//
//    @Provides
//    fun provideFishDao(database: FishRoomDatabase): FishDao {
//        return database.fishDao()
//    }
//
//}

