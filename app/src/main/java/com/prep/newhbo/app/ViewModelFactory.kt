package com.prep.newhbo.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.prep.core.domain.respository.MovieRepository
import com.prep.core.domain.usecase.AllMoviesUseCase
import com.prep.core.domain.usecase.SearchMovieUseCase
import com.prep.newhbo.features.home.MovieListViewModel
import com.prep.newhbo.features.search.SearchViewModel

class ViewModelFactory(private val repository: MovieRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieListViewModel::class.java)) {
            return MovieListViewModel(AllMoviesUseCase(repository)) as T
        } else if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(SearchMovieUseCase(repository)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}