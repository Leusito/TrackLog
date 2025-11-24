package com.example.tracklog.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "competition_table")
data class Competition(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: Long, // Epoch millis
    val name: String,
    val location: String, // "AL" or "PC"
    val events: List<CompetitionEvent>
)
