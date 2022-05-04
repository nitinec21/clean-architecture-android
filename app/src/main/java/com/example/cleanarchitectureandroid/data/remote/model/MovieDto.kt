package com.example.cleanarchitectureandroid.data.remote.model

import com.example.cleanarchitectureandroid.domain.model.Movie
import com.google.gson.annotations.SerializedName

data class MovieDto(
    val id: Int,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val title: String,
)

fun MovieDto.toMovie(): Movie {
    return Movie(
        id = id,
        posterPath = posterPath
    )
}