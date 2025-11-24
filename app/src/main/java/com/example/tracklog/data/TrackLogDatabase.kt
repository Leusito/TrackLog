package com.example.tracklog.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tracklog.data.Training
import com.example.tracklog.data.Competition
import com.example.tracklog.data.Converters
import com.example.tracklog.data.TrackLogDao

@Database(entities = [Training::class, Competition::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
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
