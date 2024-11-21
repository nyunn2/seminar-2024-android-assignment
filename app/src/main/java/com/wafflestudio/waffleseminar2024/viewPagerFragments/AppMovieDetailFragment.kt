package com.wafflestudio.waffleseminar2024.viewPagerFragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.wafflestudio.waffleseminar2024.Genre
import com.wafflestudio.waffleseminar2024.R
import com.wafflestudio.waffleseminar2024.adapter.GenreChipAdapter
import com.wafflestudio.waffleseminar2024.data.database.DbMovie
import com.wafflestudio.waffleseminar2024.viewmodel.LocalMovieViewModel
import com.wafflestudio.waffleseminar2024.data.database.MyEntity
import com.wafflestudio.waffleseminar2024.databinding.FragmentMovieDetailBinding
import com.wafflestudio.waffleseminar2024.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.DecimalFormat

@AndroidEntryPoint
class AppMovieDetailFragment : Fragment() {
    private lateinit var navController: NavController

    private val viewModel: MovieViewModel by viewModels()
    private val localViewModel: LocalMovieViewModel by viewModels()
    private val movieId: Int by lazy {
        arguments?.getInt("movieId") ?: 0
    }

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var genreChipAdapter: GenreChipAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        viewModel.fetchMovieDetails(movieId)
        viewModel.movieDetail.observe(viewLifecycleOwner) { movie ->
            movie?.let {
                it.genres?.let { it1 -> setupRecyclerView(it1) }
                binding.movieTitle.text = it.title
                binding.backdropImg.load("https://image.tmdb.org/t/p/original" + it.backdrop_path)
                binding.posterImg.load("https://image.tmdb.org/t/p/original" + it.poster_path)
                binding.ratingBar.rating = (it.vote_average ?: 10.0).toFloat()/2
                binding.rateText.text = String.format("%.1f", it.vote_average ?: 0.0)
                binding.runtimeText.text= "${it.runtime?.div(60)}h ${it.runtime?.rem(60)}m"
                binding.releaseyearText.text= it.release_date?.substring(0, 4) ?: ""
                binding.overviewText.text = it.overview
                binding.originaltitleText.text = it.original_title
                binding.statusText.text = it.status
                binding.budgetText.text = DecimalFormat("$#,###").format(it.budget)
                binding.revenueText.text = DecimalFormat("$#,###").format(it.revenue)

                val dbMovie = it.toDbMovie()
                setupFavoriteButton(dbMovie)
                updateFavoriteButtonState(it.id ?: 0)
            }
        }
    }

    private fun setupRecyclerView(data: List<Genre>) {
        genreChipAdapter = GenreChipAdapter(data)
        binding.genreRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.genreRecyclerView.adapter = genreChipAdapter
    }

    private fun setupFavoriteButton(movie: DbMovie) {
        binding.favoriteButton.setOnClickListener {
            Log.d("FavoriteButton", "Favorite button clicked")
            toggleFavorite(movie)
        }
    }

    private fun updateFavoriteButtonState(movieId: Int) {
        lifecycleScope.launch {
            val isFavorite = localViewModel.isFavorite(movieId)
            binding.favoriteButton.setImageResource(
                if (isFavorite) R.drawable.ic_star else R.drawable.ic_star_border
            )
        }
    }

    private fun toggleFavorite(movie: DbMovie) {
        lifecycleScope.launch {
            val movieId = movie.id ?: return@launch
            val isFavorite = localViewModel.isFavorite(movieId)

            Log.d("FavoriteButton", "Before toggle: isFavorite = $isFavorite")
            if (isFavorite) {
                localViewModel.removeFavorite(movie)
                Log.d("FavoriteButton", "Movie removed: $movie")
            } else {
                localViewModel.addFavorite(movie)
                Log.d("FavoriteButton", "Movie added: $movie")
            }

            delay(50)
            val updatedFavorite = localViewModel.isFavorite(movieId)
            Log.d("FavoriteButton", "After toggle: isFavorite = $updatedFavorite")

            updateFavoriteButtonState(movieId)
        }
    }

    fun MyEntity.toDbMovie(): DbMovie {
        return DbMovie(
            id = this.id,
            title = this.title,
            posterPath = this.poster_path
        )
    }
}