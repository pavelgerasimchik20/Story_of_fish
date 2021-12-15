package com.geras.fishistory.data

import kotlinx.coroutines.flow.Flow

interface FishRepository {

    val allFish: Flow<List<Fish>>

    /*suspend fun getPath(fish: Fish)*/

    suspend fun delete(fish: Fish)

    suspend fun insert(fish: Fish)
}