package com.geras.fishistory.data.database

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.geras.fishistory.data.dataclasses.Fish
import kotlinx.coroutines.flow.Flow

@androidx.room.Dao
interface FishDao {

    @Query("SELECT * FROM fish_table ")
    fun getListOfFish(): Flow<List<Fish>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: Fish)

    @Delete
    suspend fun delete(fish: Fish)

    @Query("DELETE FROM fish_table WHERE fish_name = :fish")
    suspend fun deleteByName(fish: String)


    @Query("DELETE FROM fish_table")
    suspend fun deleteAll()
}
