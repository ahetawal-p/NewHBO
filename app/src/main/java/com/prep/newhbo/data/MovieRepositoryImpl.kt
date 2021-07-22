package com.prep.newhbo.data

import com.prep.core.domain.model.ApiResult
import com.prep.core.domain.model.MovieSearchPayload
import com.prep.core.domain.respository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException


class MovieRepositoryImpl(
    private val movieService: MovieService,
    private val movieStore: MovieStore
) : MovieRepository {
    override fun movies(): String {
        return "tester"
    }

    override fun movieDetails(movieId: String): String {
        TODO("Not yet implemented")
    }

    override suspend fun searchMovie(
        searchString: String,
        pageNo: String
    ): ApiResult<MovieSearchPayload> {
        return withContext(Dispatchers.IO) {
            try {
                val response = movieService.searchMovie(searchString, pageNo)
                ApiResult.Success(response)
            } catch (e: Exception) {
                when (e) {
                    is HttpException -> {
                        ApiResult.Error(e.code(), e)
                    }
                    else -> {
                        ApiResult.Error(500, e)
                    }
                }
            }
        }
    }


}