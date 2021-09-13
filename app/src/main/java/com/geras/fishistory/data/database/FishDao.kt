package com.geras.fishistory.data.database

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.geras.fishistory.data.dataclasses.Fish
import kotlinx.coroutines.flow.Flow

@androidx.room.Dao
interface FishDao {

    @Query("SELECT * FROM fish_table ") //ORDER BY fish_name ASC
    fun getWords(): Flow<List<Fish>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: Fish)

    @Query("DELETE FROM fish_table")
    suspend fun deleteAll()
}
