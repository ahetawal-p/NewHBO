package com.prep.core.domain.respository

import com.prep.core.domain.model.MovieSearchPayload
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET(".")
    suspend fun searchMovie(
        @Query("s") searchString: String,
        @Query("page") pageNo: String = "1"
    ): MovieSearchPayload

    @GET
    suspend fun movieDetail(): String
}