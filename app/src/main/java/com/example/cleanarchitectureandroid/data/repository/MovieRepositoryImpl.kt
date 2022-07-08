package com.example.cleanarchitectureandroid.data.repository

import com.example.cleanarchitectureandroid.R
import com.example.cleanarchitectureandroid.common.Resource
import com.example.cleanarchitectureandroid.common.UiText
import com.example.cleanarchitectureandroid.data.remote.MovieApi
import com.example.cleanarchitectureandroid.data.remote.model.toMovie
import com.example.cleanarchitectureandroid.data.remote.model.toMovieDetails
import com.example.cleanarchitectureandroid.domain.model.Movie
import com.example.cleanarchitectureandroid.domain.model.MovieDetails
import com.example.cleanarchitectureandroid.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val api: MovieApi) : MovieRepository {

    override suspend fun getPopularMovies(): Flow<Resource<List<Movie>>> = flow {
        try {
            emit(Resource.Loading())
            val list = api.getPopularMovies().movieList.map { it.toMovie() }
            emit(Resource.Success(list))
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    UiText.StringResource(R.string.error_couldnt_reach_server)
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error(UiText.StringResource(R.string.oops_something_went_wrong)))
        }
    }

    override suspend fun getMovieDetails(movieId: Int): Flow<Resource<MovieDetails>> = flow {
        try {
            emit(Resource.Loading())
            val movieDetails = api.getMovieDetails(movieId).toMovieDetails()
            emit(Resource.Success(movieDetails))
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    UiText.StringResource(R.string.error_couldnt_reach_server)
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error(UiText.StringResource(R.string.oops_something_went_wrong)))
        }
    }
}