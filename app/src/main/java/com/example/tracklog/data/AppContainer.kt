package com.example.tracklog.data

import android.content.Context

interface AppContainer {
    val trackLogDao: TrackLogDao
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val trackLogDao: TrackLogDao by lazy {
        TrackLogDatabase.getDatabase(context).trackLogDao()
    }
}
