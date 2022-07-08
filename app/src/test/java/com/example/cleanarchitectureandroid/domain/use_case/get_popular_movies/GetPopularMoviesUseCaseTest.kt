package com.example.cleanarchitectureandroid.domain.use_case.get_popular_movies

import com.example.cleanarchitectureandroid.common.Resource
import com.example.cleanarchitectureandroid.common.UiText
import com.example.cleanarchitectureandroid.domain.repository.MovieRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetPopularMoviesUseCaseTest {

    @MockK
    lateinit var repository: MovieRepository

    private lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getPopularMoviesUseCase = GetPopularMoviesUseCase(repository)
    }

    @Test
    fun `when repo returns success then resource should be success`() {
        mockSuccess()
        runBlocking {
            val popularMoviesUseCase = getPopularMoviesUseCase()

            val eventCount = popularMoviesUseCase.count()
            assert(eventCount == 2)

            var resource = popularMoviesUseCase.first()
            assert(resource is Resource.Loading)

            resource = popularMoviesUseCase.last()
            assert(resource is Resource.Success)
            assert(resource.data != null)
            println(resource)
        }
    }

    @Test
    fun `when repo returns error then resource should be error`() {
        mockError()
        runBlocking {
            val popularMoviesUseCase = getPopularMoviesUseCase()

            val eventCount = popularMoviesUseCase.count()
            assert(eventCount == 2)

            var resource = popularMoviesUseCase.first()
            assert(resource is Resource.Loading)

            resource = popularMoviesUseCase.last()
            assert(resource is Resource.Error)
            println(resource)
        }
    }

    private fun mockSuccess() {
        coEvery { repository.getPopularMovies() } returns flow {
            emit(Resource.Loading())
            emit(Resource.Success(listOf())) }
    }

    private fun mockError() {
        coEvery { repository.getPopularMovies() } returns flow {
            emit(Resource.Loading())
            emit(Resource.Error(UiText.unknownError()))
        }
    }
}