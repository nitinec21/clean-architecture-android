package com.example.cleanarchitectureandroid.domain.use_case.get_movie_details

import android.content.Context
import com.example.cleanarchitectureandroid.R
import com.example.cleanarchitectureandroid.common.Resource
import com.example.cleanarchitectureandroid.data.remote.model.toMovieDetails
import com.example.cleanarchitectureandroid.domain.model.MovieDetails
import com.example.cleanarchitectureandroid.domain.repository.MovieRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val repository: MovieRepository
) {
    operator fun invoke(movieId: Int): Flow<Resource<MovieDetails>> = flow {
        try {
            emit(Resource.Loading())
            val movieDetails = repository.getMovieDetails(movieId).toMovieDetails()
            emit(Resource.Success(movieDetails))
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    e.localizedMessage ?: appContext.getString(R.string.http_error_msg)
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error(appContext.getString(R.string.io_error_msg)))
        }
    }
}