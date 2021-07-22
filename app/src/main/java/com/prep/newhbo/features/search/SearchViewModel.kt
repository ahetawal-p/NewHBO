package com.prep.newhbo.features.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prep.core.domain.model.ApiResult
import com.prep.core.domain.model.MovieSearch
import com.prep.core.domain.model.MovieSearchPayload
import com.prep.core.domain.usecase.SearchMovieUseCase
import com.prep.newhbo.app.ViewState
import com.prep.newhbo.app.extractSuccessData
import kotlinx.coroutines.launch

class SearchViewModel(private val useCase: SearchMovieUseCase) : ViewModel() {

    private val _data: MutableLiveData<ViewState<SearchResultDisplay>> = MutableLiveData()
    val data: LiveData<ViewState<SearchResultDisplay>> = _data


    private var isLoadingNextPage = false
    private var hasMorePage = false
    private var currentPage = 1
    private var totalResults = -1
    private var resultCount = 0
    private var searchStr: String? = null

    fun fetchNextPage() {
        if (resultCount == totalResults) {
            return
        }
        searchMovie(searchStr!!)
    }

    fun searchMovie(searchString: String) {
        val previousData = data.value?.extractSuccessData()
        searchStr = searchString
        viewModelScope.launch {
            val result = useCase.invoke(searchString, currentPage.toString())
            if (currentPage == 1 && totalResults == -1) {
                _data.value = ViewState.Loading
            } else {
                isLoadingNextPage = true
            }
            when (result) {
                is ApiResult.Success -> {
                    val data = result.data
                    resultCount += data.result!!.size
                    totalResults = data.totalResults!!.toInt()
                    var newData = SearchResultDisplay.from(data, resultCount)
                    if (currentPage != 1 && previousData != null) {
                        newData = mergeData(previousData, newData)
                    }
                    _data.value = ViewState.Success(newData)
                    ++currentPage
                }
                is ApiResult.Error -> {
                    _data.value = ViewState.Error(result)
                }
            }
        }
    }

    private fun mergeData(
        prevData: SearchResultDisplay,
        newData: SearchResultDisplay
    ): SearchResultDisplay {
        val updatedList = mutableListOf<MovieSearch>()
        updatedList.addAll(prevData.results)
        updatedList.addAll(newData.results)
        return SearchResultDisplay(updatedList, newData.hasMore)
    }
}

data class SearchResultDisplay(
    val results: List<MovieSearch>,
    val hasMore: Boolean
) {


    companion object {
        fun from(
            payload: MovieSearchPayload,
            currentCount: Int
        ): SearchResultDisplay {
            val resultList = payload.result!!.asList()
            val totalResults = payload.totalResults!!.toInt()
            if (currentCount < totalResults) {
                return SearchResultDisplay(resultList, true)
            }
            return SearchResultDisplay(resultList, false)
        }
    }


}