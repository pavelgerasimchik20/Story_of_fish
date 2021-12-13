package com.geras.fishistory

import android.app.Application
import com.geras.fishistory.data.FishDao
import com.geras.fishistory.data.FishRoomDatabase
import com.geras.fishistory.data.FishRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class FisHistoryApplication : Application() {

    val database by lazy { FishRoomDatabase.getDatabase(this) }
    val repository by lazy { FishRepository(database.fishDao()) }
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

