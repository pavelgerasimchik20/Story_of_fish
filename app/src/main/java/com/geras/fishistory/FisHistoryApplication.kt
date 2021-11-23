package com.geras.fishistory

import android.app.Application
import com.geras.fishistory.data.FishRoomDatabase
import com.geras.fishistory.data.FishRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class FisHistoryApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { FishRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { FishRepository(database.fishDao()) }
}