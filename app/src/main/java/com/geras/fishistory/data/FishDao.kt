package com.geras.fishistory.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@androidx.room.Dao
interface FishDao {

    @Query("SELECT * FROM fish_table ")
    fun getListOfFish(): Flow<List<Fish>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(fish: Fish)

    @Delete
    suspend fun delete(fish: Fish)

    @Query("DELETE FROM fish_table WHERE fish_name = :fish")
    suspend fun deleteByName(fish: String)


    @Query("DELETE FROM fish_table")
    suspend fun deleteAll()

    @Update
    suspend fun update(fish: Fish)
}
