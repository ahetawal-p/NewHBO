package com.prep.core.domain.usecase

import com.prep.core.domain.model.ApiResult
import com.prep.core.domain.model.MovieSearchPayload
import com.prep.core.domain.respository.MovieRepository

class SearchMovieUseCase(private val repository: MovieRepository) {

    suspend fun invoke(searchString: String, pageNo: String): ApiResult<MovieSearchPayload> {
        return when (val result = repository.searchMovie(searchString, pageNo)) {
            is ApiResult.Success -> {
                val status = result.data.status.toBoolean()
                return if (status) {
                    result
                } else {
                    ApiResult.Error(500, Exception(result.data.error))
                }
            }
            else -> {
                result
            }
        }
    }
}