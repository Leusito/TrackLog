package com.example.tracklog.data

data class CompetitionEvent(

    val performance: String,
    val distanceMeters: Int?, // Used for wind validation logic
    val wind: String? = null,
    val isInvalid: Boolean = false
)
