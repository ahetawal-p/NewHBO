package com.prep.newhbo.features.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.prep.newhbo.R
import com.prep.newhbo.app.AppDependency
import com.prep.newhbo.app.ViewModelFactory
import com.prep.newhbo.data.MovieRepositoryImpl
import com.prep.newhbo.databinding.MoviewListFragmentBinding

class MovieListFragment : Fragment() {

    private lateinit var viewModel: MovieListViewModel

    private var _binding: MoviewListFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onAttach(context: Context) {
        super.onAttach(context)
        val dependency = (context.applicationContext as AppDependency)
        val repo =
            MovieRepositoryImpl(dependency.getMovieService(), dependency.getMovieStore())
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(repo)
        ).get(MovieListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.printMe()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MoviewListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.clickMe.setOnClickListener {
            findNavController().navigate(R.id.action_moviewList_to_movieDetails)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}