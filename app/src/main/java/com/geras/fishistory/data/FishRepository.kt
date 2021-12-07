package com.geras.fishistory.data

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class FishRepository(private val fishDao: FishDao) {

    val allFish: Flow<List<Fish>> = fishDao.getListOfFish()

    @WorkerThread
    suspend fun delete(fish: Fish) {
        fishDao.delete(fish)
    }

    @WorkerThread
    suspend fun deleteByName(fish: Fish) {
        fishDao.deleteByName(fish.name)
    }

    @WorkerThread
    suspend fun insert(fish: Fish) {
        fishDao.insert(fish)
    }

    @WorkerThread
    suspend fun update(fish: Fish) {
        fishDao.update(fish)
    }
}
