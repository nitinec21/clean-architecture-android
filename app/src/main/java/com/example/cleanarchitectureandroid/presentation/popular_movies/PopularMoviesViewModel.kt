package com.example.cleanarchitectureandroid.presentation.popular_movies

import com.example.cleanarchitectureandroid.common.Resource
import com.example.cleanarchitectureandroid.core.dispatchers.Dispatcher
import com.example.cleanarchitectureandroid.core.viewmodel.BaseViewModel
import com.example.cleanarchitectureandroid.domain.model.Movie
import com.example.cleanarchitectureandroid.domain.use_case.get_popular_movies.GetPopularMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase, appDispatcher: Dispatcher
) : BaseViewModel(appDispatcher) {

    private val _movies = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading())
    val movies = _movies.asStateFlow()

    init {
        getPopularMovies()
    }

    private fun getPopularMovies() {
        launchOnMain {
            getPopularMoviesUseCase().collect {
                _movies.value = it
            }
        }
    }
}