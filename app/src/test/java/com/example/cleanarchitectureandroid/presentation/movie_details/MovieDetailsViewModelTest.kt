package com.example.cleanarchitectureandroid.presentation.movie_details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.cleanarchitectureandroid.common.Resource
import com.example.cleanarchitectureandroid.domain.model.MovieDetails
import com.example.cleanarchitectureandroid.domain.use_case.get_movie_details.GetMovieDetailsUseCase
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

class MovieDetailsViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @MockK
    private lateinit var apiObserver: Observer<Resource<MovieDetails>>

    @MockK
    lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase

    private lateinit var movieDetailsViewModel: MovieDetailsViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        movieDetailsViewModel = MovieDetailsViewModel(getMovieDetailsUseCase)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when use case returns success then resource returned should be success`() {
        runTest {
            every { apiObserver.onChanged(any()) } answers { }
            every { getMovieDetailsUseCase(1) } returns flow { emit(Resource.Success(MovieDetails())) }

            movieDetailsViewModel.getMovieDetails(1)
            verify(exactly = 1) { getMovieDetailsUseCase(1) }
            movieDetailsViewModel.movieDetails.observeForever(apiObserver)

            assert(movieDetailsViewModel.movieDetails.value != null)
            assert(movieDetailsViewModel.movieDetails.value is Resource.Success)
            assert(movieDetailsViewModel.movieDetails.value!!.data != null)

            movieDetailsViewModel.movieDetails.removeObserver(apiObserver)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when use case returns error then resource returned should be error`() {
        runTest {
            every { apiObserver.onChanged(any()) } answers { }
            every { getMovieDetailsUseCase(1) } returns flow { emit(Resource.Error("Some error")) }

            movieDetailsViewModel.getMovieDetails(1)
            verify(exactly = 1) { getMovieDetailsUseCase(1) }
            movieDetailsViewModel.movieDetails.observeForever(apiObserver)

            assert(movieDetailsViewModel.movieDetails.value != null)
            assert(movieDetailsViewModel.movieDetails.value is Resource.Error)
            assert(movieDetailsViewModel.movieDetails.value!!.message != null)

            movieDetailsViewModel.movieDetails.removeObserver(apiObserver)
        }
    }
}