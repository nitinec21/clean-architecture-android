package com.example.cleanarchitectureandroid.data.repository

import com.example.cleanarchitectureandroid.data.remote.MovieApi
import com.example.cleanarchitectureandroid.data.remote.model.MovieDetailsDto
import com.example.cleanarchitectureandroid.data.remote.model.MovieResponse
import com.example.cleanarchitectureandroid.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val api: MovieApi) : MovieRepository {

    override suspend fun getPopularMovies(): MovieResponse {
        return api.getPopularMovies()
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDetailsDto {
        return api.getMovieDetails(movieId)
    }
}