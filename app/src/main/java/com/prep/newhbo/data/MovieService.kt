package com.prep.newhbo.data

import com.prep.core.domain.model.MovieSearchPayload
import com.prep.core.domain.respository.MovieApi
import retrofit2.Retrofit

class MovieService(private val retrofit: Retrofit) : MovieApi {
    private val moviesApi by lazy { retrofit.create(MovieApi::class.java) }


    override suspend fun searchMovie(
        searchString: String,
        pageNo: String
    ): MovieSearchPayload {
        return moviesApi.searchMovie(searchString, pageNo)
    }

    override suspend fun movieDetail() = moviesApi.movieDetail()
}