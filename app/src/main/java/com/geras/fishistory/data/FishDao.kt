package com.geras.fishistory.data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@androidx.room.Dao
interface FishDao {

    @Query("SELECT * FROM fish_table ")
    fun getListOfFish(): Flow<List<Fish>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(fish: Fish)

    @Delete
    suspend fun delete(fish: Fish)

    @Query("DELETE FROM fish_table WHERE name = :fish")
    suspend fun deleteByName(fish: String)

    @Query("DELETE FROM fish_table")
    suspend fun deleteAll()

    /*@Query("SELECT * FROM fish_table WHERE photoPath = :fish")
    suspend fun getPath(fish: Fish): Flow<String>*/

}
