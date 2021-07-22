package com.prep.core.domain.respository

import com.prep.core.domain.model.ApiResult
import com.prep.core.domain.model.MovieSearchPayload

interface MovieRepository {
    fun movies(): String
    fun movieDetails(movieId: String): String
    suspend fun searchMovie(searchString: String, pageNo: String): ApiResult<MovieSearchPayload>
}