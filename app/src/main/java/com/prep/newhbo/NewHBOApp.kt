package com.prep.newhbo

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.prep.core.domain.respository.RetrofitBuilder
import com.prep.newhbo.app.AppDependency
import com.prep.newhbo.data.MovieService
import com.prep.newhbo.data.MovieStore

class NewHBOApp : Application(), AppDependency {

    private lateinit var movieService: MovieService
    private lateinit var sharedPref: SharedPreferences
    private lateinit var movieStore: MovieStore

    override fun onCreate() {
        super.onCreate()

        movieService = MovieService(RetrofitBuilder.retrofit)
        sharedPref = getSharedPreferences("movies", Context.MODE_PRIVATE)
        movieStore = MovieStore(sharedPref)
    }

    override fun getMovieService(): MovieService {
        return movieService
    }

    override fun getMovieStore(): MovieStore {
        return movieStore
    }

//    override fun getSharedPref(): SharedPreferences {
//        return sharedPref
//    }
}