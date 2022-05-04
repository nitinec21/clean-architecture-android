package com.example.cleanarchitectureandroid.domain.use_case.get_popular_movies

import android.content.Context
import com.example.cleanarchitectureandroid.R
import com.example.cleanarchitectureandroid.common.Resource
import com.example.cleanarchitectureandroid.data.remote.model.toMovie
import com.example.cleanarchitectureandroid.domain.model.Movie
import com.example.cleanarchitectureandroid.domain.repository.MovieRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val repository: MovieRepository
) {
    operator fun invoke(): Flow<Resource<List<Movie>>> = flow {
        try {
            emit(Resource.Loading())
            val list = repository.getPopularMovies().movieList.map { it.toMovie() }
            emit(Resource.Success(list))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: appContext.getString(R.string.http_error_msg)))
        } catch(e: IOException) {
            emit(Resource.Error(appContext.getString(R.string.io_error_msg)))
        }
    }
}