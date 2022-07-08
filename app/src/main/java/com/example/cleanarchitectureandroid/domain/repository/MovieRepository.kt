package com.example.cleanarchitectureandroid.domain.repository

import com.example.cleanarchitectureandroid.common.Resource
import com.example.cleanarchitectureandroid.domain.model.Movie
import com.example.cleanarchitectureandroid.domain.model.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getPopularMovies(): Flow<Resource<List<Movie>>>

    suspend fun getMovieDetails(movieId: Int): Flow<Resource<MovieDetails>>
}