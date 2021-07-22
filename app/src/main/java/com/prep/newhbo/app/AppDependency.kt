package com.prep.newhbo.app

import com.prep.newhbo.data.MovieService
import com.prep.newhbo.data.MovieStore

interface AppDependency {

    fun getMovieService(): MovieService

    fun getMovieStore(): MovieStore
}