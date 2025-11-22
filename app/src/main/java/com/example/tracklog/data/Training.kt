package com.example.tracklog.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "training_table")
data class Training(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: Long, // Epoch millis
    val description: String,
    val distanceMeters: Int,
    val times: List<String>,
    val notes: String
)
