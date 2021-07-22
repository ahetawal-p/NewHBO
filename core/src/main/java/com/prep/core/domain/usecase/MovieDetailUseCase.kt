package com.prep.core.domain.usecase

import com.prep.core.domain.respository.MovieRepository

class MovieDetailUseCase(private val repository: MovieRepository) {

    suspend fun invoke(movieId: String): String {
        return repository.movieDetails(movieId)
    }
}