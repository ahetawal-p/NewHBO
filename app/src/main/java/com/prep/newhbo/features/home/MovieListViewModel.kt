package com.prep.newhbo.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prep.core.domain.usecase.AllMoviesUseCase
import com.prep.core.domain.usecase.None
import kotlinx.coroutines.launch

class MovieListViewModel(private val allMovies: AllMoviesUseCase) : ViewModel() {


    fun printMe() {
        println("I am viewmodel")
        viewModelScope.launch {
            println(allMovies.invoke(None()))
        }

    }
}