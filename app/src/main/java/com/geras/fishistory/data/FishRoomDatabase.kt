package com.geras.fishistory.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Fish::class], version = 1)
abstract class FishRoomDatabase : RoomDatabase() {
    abstract fun fishDao(): FishDao

    companion object {
        @Volatile
        private var INSTANCE: FishRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): FishRoomDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FishRoomDatabase::class.java,
                    "fish_database"
                )
                    .addCallback(FishDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class FishDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.fishDao())
                    }
                }
            }
        }

        suspend fun populateDatabase(fishDao: FishDao) {
            fishDao.deleteAll()
        }
    }
}
