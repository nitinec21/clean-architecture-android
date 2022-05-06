package com.example.cleanarchitectureandroid.domain.model

data class MovieDetails(
    val id: Int = 0,
    val overview: String = "",
    val backdropPath: String = "",
    val releaseDate: String = "",
    val tagline: String = "",
    val title: String = "",
    val rating: Double = 0.0
)