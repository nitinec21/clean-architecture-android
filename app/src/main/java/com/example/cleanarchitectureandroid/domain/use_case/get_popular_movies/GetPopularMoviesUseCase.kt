package com.example.cleanarchitectureandroid.domain.use_case.get_popular_movies

import com.example.cleanarchitectureandroid.domain.repository.MovieRepository
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke() = repository.getPopularMovies()
}