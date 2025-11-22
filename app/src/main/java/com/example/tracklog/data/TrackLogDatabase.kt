package com.example.tracklog.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Training::class, Competition::class], version = 1, exportSchema = false)
abstract class TrackLogDatabase : RoomDatabase() {

    abstract fun trackLogDao(): TrackLogDao

    companion object {
        @Volatile
        private var Instance: TrackLogDatabase? = null

        fun getDatabase(context: Context): TrackLogDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, TrackLogDatabase::class.java, "tracklog_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
