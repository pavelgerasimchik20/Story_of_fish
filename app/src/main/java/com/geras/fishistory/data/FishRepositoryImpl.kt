package com.geras.fishistory.data

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FishRepositoryImpl @Inject constructor(private val fishDao: FishDao): FishRepository {

    override val allFish: Flow<List<Fish>> = fishDao.getListOfFish()

    @WorkerThread
    override suspend fun delete(fish: Fish) {
        fishDao.delete(fish)
    }

    @WorkerThread
    override suspend fun insert(fish: Fish) {
        fishDao.insert(fish)
    }
}
