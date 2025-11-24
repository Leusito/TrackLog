package com.example.tracklog.data

import androidx.room.Entity
import androidx.room.PrimaryKey

data class TrainingDistance(
    val distanceMeters: Int,
    val times: List<String>
)

@Entity(tableName = "training_table")
data class Training(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: Long, // Epoch millis
    val description: String,
    val distances: List<TrainingDistance>,
    val notes: String
)
