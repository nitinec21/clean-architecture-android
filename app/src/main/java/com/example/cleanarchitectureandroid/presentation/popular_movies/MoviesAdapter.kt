package com.example.cleanarchitectureandroid.presentation.popular_movies

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cleanarchitectureandroid.common.Constants.POSTER_BASE_URL
import com.example.cleanarchitectureandroid.databinding.ItemMovieBinding
import com.example.cleanarchitectureandroid.domain.model.Movie

class MoviesAdapter(
    private val listener: MovieItemListener,
    private val movieList: ArrayList<Movie>
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    interface MovieItemListener {
        fun onClickedItem(movieId: Int)
    }

    class MovieViewHolder(
        private val binding: ItemMovieBinding,
        private val listener: MovieItemListener
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        private lateinit var movie: Movie

        init {
            binding.moviePoster.setOnClickListener(this)
        }

        fun bind(movie: Movie) {
            this.movie = movie
            val moviePosterURL = POSTER_BASE_URL + movie.posterPath
            Glide.with(binding.root).load(moviePosterURL).into(binding.moviePoster)
        }

        override fun onClick(v: View?) {
            movie.id.let { listener.onClickedItem(it) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = movieList.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.bind(movieList[position])

    @SuppressLint("NotifyDataSetChanged")
    fun addData(list: List<Movie>) {
        movieList.addAll(list)
        notifyDataSetChanged()
    }
}