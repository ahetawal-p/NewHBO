package com.prep.newhbo.features.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.prep.core.domain.model.MovieSearch
import com.prep.newhbo.databinding.SearchResultItemBinding

typealias OnClickResult = (MovieSearch) -> Unit
typealias OnClickFav = (MovieSearch) -> Unit

class SearchResultAdapter : ListAdapter<MovieSearch, RecyclerView.ViewHolder>(MovieSearchDiff()) {
    var onClickResult: OnClickResult? = null
    var onClickFav: OnClickFav? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SearchResultItemViewHolder(
            SearchResultItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        (holder as SearchResultItemViewHolder).bind(
            getItem(position),
            { onClickResult?.invoke(it) },
            { onClickFav?.invoke(it) })
}

class SearchResultItemViewHolder(binding: SearchResultItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private val titleView = binding.title
    private val logoImageView = binding.logo
    private val yearTextView = binding.year
    private val favView = binding.fav
    private val container = binding.container

    fun bind(
        item: MovieSearch,
        onSearchClickListener: (MovieSearch) -> Unit,
        onFavClickListener: (MovieSearch) -> Unit,
    ) {
        item.let { it ->
            titleView.text = it.title
            yearTextView.text = it.year
            logoImageView.load(it.img) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
            container.setOnClickListener { onSearchClickListener(item) }
            favView.setOnClickListener { fav ->
                fav.isSelected = !fav.isSelected
                onFavClickListener(item)
            }
        }
    }


}


private class MovieSearchDiff : DiffUtil.ItemCallback<MovieSearch>() {
    override fun areItemsTheSame(oldItem: MovieSearch, newItem: MovieSearch) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: MovieSearch, newItem: MovieSearch) =
        oldItem.id == newItem.id
}