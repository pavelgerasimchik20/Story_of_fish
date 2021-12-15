package com.geras.fishistory.di.modules

import android.content.Context
import com.geras.fishistory.data.FishDao
import com.geras.fishistory.data.FishRoomDatabase
import dagger.Module
import dagger.Provides

@Module
class DataBaseModule {

    @Provides
    fun provideFishRoomDatabase(context: Context): FishRoomDatabase {
        return FishRoomDatabase.getDatabase(context)
    }

    @Provides
    fun provideFishDao(database: FishRoomDatabase): FishDao {
        return database.fishDao()
    }
}