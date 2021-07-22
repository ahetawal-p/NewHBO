package com.prep.core.domain.usecase

import com.prep.core.domain.respository.MovieRepository

class AllMoviesUseCase(private val repository: MovieRepository) {

    suspend fun invoke(input: None): String {
        return repository.movies()
    }

}