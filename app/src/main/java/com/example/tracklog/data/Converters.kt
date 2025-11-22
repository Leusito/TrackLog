package com.example.tracklog.data

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromString(value: String): List<String> {
        if (value.isBlank()) return emptyList()
        return value.split(",").map { it.trim() }
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        return list.joinToString(",")
    }
}
