package com.example.cleanarchitectureandroid.domain.use_case.get_movie_details

import com.example.cleanarchitectureandroid.common.Resource
import com.example.cleanarchitectureandroid.common.UiText
import com.example.cleanarchitectureandroid.domain.model.MovieDetails
import com.example.cleanarchitectureandroid.domain.repository.MovieRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetMovieDetailsUseCaseTest {

    @MockK
    lateinit var repository: MovieRepository

    private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getMovieDetailsUseCase = GetMovieDetailsUseCase(repository)
    }

    @Test
    fun `when repo returns success then resource returned should be success`() {
        mockSuccess()
        runTest {
            val getMovieDetailsUseCase = getMovieDetailsUseCase(1)

            val eventCount = getMovieDetailsUseCase.count()
            assert(eventCount == 2)

            var resource = getMovieDetailsUseCase.first()
            assert(resource is Resource.Loading)

            resource = getMovieDetailsUseCase.last()
            assert(resource is Resource.Success)
            assert(resource.data != null)
            println(resource)
        }
    }

    @Test
    fun `when repo returns error then resource returned should be error`() {
        mockError()
        runTest {
            val getMovieDetailsUseCase = getMovieDetailsUseCase(1)

            val eventCount = getMovieDetailsUseCase.count()
            assert(eventCount == 2)

            var resource = getMovieDetailsUseCase.first()
            assert(resource is Resource.Loading)

            resource = getMovieDetailsUseCase.last()
            assert(resource is Resource.Error)
            println(resource)
        }
    }

    private fun mockSuccess() {
        coEvery { repository.getMovieDetails(any()) } returns flow {
            emit(Resource.Loading())
            emit(Resource.Success(MovieDetails()))
        }
    }

    private fun mockError() {
        coEvery { repository.getMovieDetails(any()) } returns flow {
            emit(Resource.Loading())
            emit(Resource.Error(UiText.unknownError()))
        }
    }
}