package com.example.hewannusantara

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Hewan::class], version = 1)
abstract class HewanDatabase : RoomDatabase() {
    abstract fun hewanDao(): HewanDao

    companion object {
        @Volatile
        private var INSTANCE: HewanDatabase? = null

        fun getInstance(context: Context): HewanDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HewanDatabase::class.java,
                    "hewan_database"
                ).allowMainThreadQueries() // hanya untuk testing/latihan
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
