package com.example.cleanarchitectureandroid.presentation.popular_movies

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cleanarchitectureandroid.R
import com.example.cleanarchitectureandroid.common.Constants
import com.example.cleanarchitectureandroid.common.Resource
import com.example.cleanarchitectureandroid.databinding.MovieFragmentBinding
import com.example.cleanarchitectureandroid.domain.model.Movie
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PopularMoviesFragment : Fragment(), MoviesAdapter.MovieItemListener {

    private lateinit var binding: MovieFragmentBinding
    private val viewModel: PopularMoviesViewModel by viewModels()
    private lateinit var movieAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = MovieFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onClickedItem(movieId: Int) {
        findNavController().navigate(
            R.id.action_movieFragment_to_movieDetailsFragment,
            bundleOf(Constants.PARAM_MOVIE_ID to movieId)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObserver()
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movies.collect {
                    when (it) {
                        is Resource.Success -> {
                            binding.progressBar.visibility = View.GONE
                            it.data?.let { moviesList -> renderList(moviesList) }
                            binding.moviesRv.visibility = View.VISIBLE
                        }
                        is Resource.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.moviesRv.visibility = View.GONE
                        }
                        is Resource.Error -> {
                            binding.progressBar.visibility = View.GONE
                            binding.errorTxt.visibility = View.VISIBLE
                            binding.errorTxt.text = it.uiText?.asString(requireContext()) ?: getString(R.string.error_unknown)
                        }
                    }
                }
            }
        }
    }

    private fun renderList(movies: List<Movie>) {
        movieAdapter.addData(movies)
    }

    private fun setupUI() {
        binding.moviesRv.apply {
            layoutManager = GridLayoutManager(
                context,
                if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 3 else 6
            )
            setHasFixedSize(true)
        }
        movieAdapter = MoviesAdapter(this, arrayListOf())
        binding.moviesRv.adapter = movieAdapter
    }
}