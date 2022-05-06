package com.example.cleanarchitectureandroid.data.remote.model


import com.example.cleanarchitectureandroid.domain.model.MovieDetails
import com.google.gson.annotations.SerializedName

data class MovieDetailsDto(
    val budget: Int = 0,
    val id: Int = 0,
    val overview: String = "",
    val popularity: Double = 0.0,
    @SerializedName("poster_path")
    val posterPath: String = "",
    @SerializedName("backdrop_path")
    val backdropPath: String = "",
    @SerializedName("release_date")
    val releaseDate: String = "",
    val revenue: Long = 0,
    val runtime: Int = 0,
    val status: String = "",
    val tagline: String = "",
    val title: String = "",
    val video: Boolean = false,
    @SerializedName("vote_average")
    val rating: Double = 0.0
)

fun MovieDetailsDto.toMovieDetails(): MovieDetails {
    return MovieDetails(
        id = id,
        overview = overview,
        backdropPath = backdropPath,
        releaseDate = releaseDate,
        tagline = tagline,
        title = title,
        rating = rating
    )
}