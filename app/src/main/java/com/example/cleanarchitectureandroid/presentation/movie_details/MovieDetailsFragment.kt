package com.example.cleanarchitectureandroid.presentation.movie_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.cleanarchitectureandroid.R
import com.example.cleanarchitectureandroid.common.Constants
import com.example.cleanarchitectureandroid.common.Constants.POSTER_BASE_URL
import com.example.cleanarchitectureandroid.common.Resource
import com.example.cleanarchitectureandroid.databinding.MovieDetailsFragmentBinding
import com.example.cleanarchitectureandroid.domain.model.MovieDetails
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private lateinit var binding: MovieDetailsFragmentBinding
    private val viewModel: MovieDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MovieDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt(Constants.PARAM_MOVIE_ID)?.let {
            viewModel.getMovieDetails(it)
        }
        setupObservers()
    }

    private fun setupObservers() {

        viewModel.movieDetails.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    bindData(it.data)
                    binding.progressBar.visibility = View.GONE
                    binding.linearLayout.visibility = View.VISIBLE
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.linearLayout.visibility = View.GONE
                }
            }
        }
    }

    private fun bindData(movieDetails: MovieDetails?) {
        if (movieDetails != null) {
            binding.movieDetails = movieDetails
            val moviePosterURL = POSTER_BASE_URL + movieDetails.backdropPath
            Glide.with(this).load(moviePosterURL).into(binding.ivMoviePoster)
        } else {
            Toast.makeText(context, getString(R.string.general_error_msg), Toast.LENGTH_SHORT).show()
        }
    }
}