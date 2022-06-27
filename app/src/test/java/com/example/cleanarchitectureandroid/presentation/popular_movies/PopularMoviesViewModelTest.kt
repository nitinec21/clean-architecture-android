package com.example.cleanarchitectureandroid.presentation.popular_movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.cleanarchitectureandroid.common.Resource
import com.example.cleanarchitectureandroid.domain.model.Movie
import com.example.cleanarchitectureandroid.domain.use_case.get_popular_movies.GetPopularMoviesUseCase
import com.example.cleanarchitectureandroid.utils.TestCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest

import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PopularMoviesViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @MockK
    private lateinit var apiObserver: Observer<Resource<List<Movie>>>

    @MockK
    lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase

    private lateinit var popularMoviesViewModel: PopularMoviesViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when use case returns success then resource should be success`() {
        runTest {
            every { apiObserver.onChanged(any()) } answers { }
            every { getPopularMoviesUseCase() } returns flow { emit(Resource.Success(listOf())) }

            popularMoviesViewModel = PopularMoviesViewModel(getPopularMoviesUseCase)

            verify(exactly = 1) { getPopularMoviesUseCase() }

            popularMoviesViewModel.movies.observeForever(apiObserver)

            assert(popularMoviesViewModel.movies.value != null)
            assert(popularMoviesViewModel.movies.value is Resource.Success)
            assert(popularMoviesViewModel.movies.value!!.data != null)

            popularMoviesViewModel.movies.removeObserver(apiObserver)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when use case returns error then resource should be error`() {
        runTest{
            every { apiObserver.onChanged(any()) } answers { }
            every { getPopularMoviesUseCase() } returns flow { emit(Resource.Error("Some error")) }

            popularMoviesViewModel = PopularMoviesViewModel(getPopularMoviesUseCase)

            verify(exactly = 1) { getPopularMoviesUseCase() }

            popularMoviesViewModel.movies.observeForever(apiObserver)

            assert(popularMoviesViewModel.movies.value != null)
            assert(popularMoviesViewModel.movies.value is Resource.Error)
            assert(popularMoviesViewModel.movies.value!!.message != null)

            popularMoviesViewModel.movies.removeObserver(apiObserver)
        }
    }
}