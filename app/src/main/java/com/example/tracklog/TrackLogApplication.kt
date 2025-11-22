package com.example.tracklog

import android.app.Application
import com.example.tracklog.data.AppContainer
import com.example.tracklog.data.AppDataContainer

class TrackLogApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
