package com.geras.fishistory.data.database

import androidx.annotation.WorkerThread
import com.geras.fishistory.data.dataclasses.Fish
import kotlinx.coroutines.flow.Flow

class FishRepository(private val fishDao: FishDao) {

    val allFish: Flow<List<Fish>> = fishDao.getListOfFish()

    @WorkerThread
    suspend fun insert(fish: Fish) {
        fishDao.insert(fish)
    }
}
