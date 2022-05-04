package com.example.cleanarchitectureandroid.domain.repository

import com.example.cleanarchitectureandroid.data.remote.model.MovieDetailsDto
import com.example.cleanarchitectureandroid.data.remote.model.MovieResponse

interface MovieRepository {

    suspend fun getPopularMovies(): MovieResponse

    suspend fun getMovieDetails(movieId: Int): MovieDetailsDto
}