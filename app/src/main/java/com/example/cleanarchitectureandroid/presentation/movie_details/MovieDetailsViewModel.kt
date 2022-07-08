package com.example.cleanarchitectureandroid.presentation.movie_details

import com.example.cleanarchitectureandroid.common.Resource
import com.example.cleanarchitectureandroid.core.dispatchers.Dispatcher
import com.example.cleanarchitectureandroid.core.viewmodel.BaseViewModel
import com.example.cleanarchitectureandroid.domain.model.MovieDetails
import com.example.cleanarchitectureandroid.domain.use_case.get_movie_details.GetMovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase, appDispatcher: Dispatcher
) : BaseViewModel(appDispatcher) {

    private val _movieDetails = MutableStateFlow<Resource<MovieDetails>>(Resource.Loading())
    val movieDetails = _movieDetails.asStateFlow()

    fun getMovieDetails(movieId: Int) {
        launchOnMain {
            getMovieDetailsUseCase(movieId).collect {
                _movieDetails.value = it
            }
        }
    }
}