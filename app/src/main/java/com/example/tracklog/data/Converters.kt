package com.example.tracklog.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String): List<String> {
        if (value.isBlank()) return emptyList()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromTrainingDistanceList(value: String): List<TrainingDistance> {
        if (value.isBlank()) return emptyList()
        val type = object : TypeToken<List<TrainingDistance>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun toTrainingDistanceList(list: List<TrainingDistance>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromCompetitionEventList(value: String): List<CompetitionEvent> {
        if (value.isBlank()) return emptyList()
        val type = object : TypeToken<List<CompetitionEvent>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun toCompetitionEventList(list: List<CompetitionEvent>): String {
        return gson.toJson(list)
    }
}
