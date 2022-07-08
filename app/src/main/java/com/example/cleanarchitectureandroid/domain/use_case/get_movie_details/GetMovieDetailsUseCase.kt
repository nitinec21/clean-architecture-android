package com.example.cleanarchitectureandroid.domain.use_case.get_movie_details

import com.example.cleanarchitectureandroid.common.Resource
import com.example.cleanarchitectureandroid.domain.model.MovieDetails
import com.example.cleanarchitectureandroid.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): Flow<Resource<MovieDetails>> =
        repository.getMovieDetails(movieId)
}