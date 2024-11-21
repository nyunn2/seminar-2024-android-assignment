package com.wafflestudio.waffleseminar2024.viewPagerFragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wafflestudio.waffleseminar2024.R
import com.wafflestudio.waffleseminar2024.adapter.FavoriteMoviesAdapter
import com.wafflestudio.waffleseminar2024.viewmodel.LocalMovieViewModel
import com.wafflestudio.waffleseminar2024.databinding.FragmentFavoriteMoviesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteMoviesFragment : Fragment() {
    private lateinit var navController: NavController
    private val localViewModel: LocalMovieViewModel by viewModels()
    private var _binding: FragmentFavoriteMoviesBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: FavoriteMoviesAdapter
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        binding.recyclerView.adapter = null
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        navController = findNavController()

        localViewModel.favoriteMovies.observe(viewLifecycleOwner) { movies ->
            Log.d("AppFragment", "Observed favorites: $movies")
            adapter.submitList(movies)
        }

    }

    private fun setupRecyclerView(){
        adapter = FavoriteMoviesAdapter { movie ->
            val movieId = movie.id ?: 0
            val bundle = Bundle().apply {
                putInt("movieId", movieId)
            }
            navController.navigate(R.id.AppMovieDetailFragment, bundle)
        }

        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.recyclerView.adapter = adapter
    }
}