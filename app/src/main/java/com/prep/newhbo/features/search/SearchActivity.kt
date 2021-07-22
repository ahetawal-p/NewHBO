package com.prep.newhbo.features.search

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ConcatAdapter
import com.prep.newhbo.app.*
import com.prep.newhbo.data.MovieRepositoryImpl
import com.prep.newhbo.databinding.ActivitySearchBinding
import com.prep.newhbo.databinding.ListItemLoadingBinding

class SearchActivity : AppCompatActivity() {

    private lateinit var viewModel: SearchViewModel

    private var _binding: ActivitySearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Results"

        setupDependencies()

        val searchResultsAdapter = SearchResultAdapter()
        val loadingAdapter =
            SingleItemAdapter((ListItemLoadingBinding::inflate)) { binding, hasMore: Boolean ->
                binding.root.isGone = !hasMore
            }

        binding.searchResults.adapter = ConcatAdapter(searchResultsAdapter, loadingAdapter)
        val scrollListener = InfiniteListScrollListener {
            viewModel.fetchNextPage()
        }
        binding.searchResults.addOnScrollListener(scrollListener)

        searchResultsAdapter.onClickResult = {
            println("Result $it")
        }

        searchResultsAdapter.onClickFav = {
            println("Fav $it")
        }

        viewModel.data.observe(this) { state ->
            when (state) {
                is ViewState.Success -> {
                    searchResultsAdapter.submitList(state.data.results)
                    loadingAdapter.value = state.data.hasMore
                    binding.progressBar.isGone = true
                    binding.searchResults.isGone = false
                }
                is ViewState.Error -> {

                }
                ViewState.Loading -> {

                }
                else -> {
                    // no nothing
                }
            }
        }

        handleIntent(intent)
    }

    private fun setupDependencies() {
        val dependency = (applicationContext as AppDependency)
        val repo =
            MovieRepositoryImpl(dependency.getMovieService(), dependency.getMovieStore())
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(repo)
        ).get(SearchViewModel::class.java)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)!!
            viewModel.searchMovie(query)
        }
    }
}