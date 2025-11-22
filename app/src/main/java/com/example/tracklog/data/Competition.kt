package com.example.tracklog.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "competition_table")
data class Competition(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: Long, // Epoch millis
    val name: String,
    val event: String, // e.g., "100m", "Long Jump"
    val result: String, // e.g., "10.5s", "7.20m"
    val position: Int
)
