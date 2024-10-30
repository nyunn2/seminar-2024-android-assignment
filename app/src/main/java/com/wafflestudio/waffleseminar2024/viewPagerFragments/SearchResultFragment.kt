package com.wafflestudio.waffleseminar2024.viewPagerFragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wafflestudio.waffleseminar2024.Movie
import com.wafflestudio.waffleseminar2024.MovieData
import com.wafflestudio.waffleseminar2024.R
import com.wafflestudio.waffleseminar2024.databinding.FragmentSearchBinding
import com.wafflestudio.waffleseminar2024.searchResultRecyclerViewAdapter

class SearchResultFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchResultRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.genreRecyclerView.visibility = View.GONE
        binding.searchResultRecyclerView.visibility = View.VISIBLE

        searchResultRecyclerView = binding.searchResultRecyclerView
        val genreId = arguments?.getInt("genreId")
        val searchQuery = arguments?.getString("searchQuery")
        Log.d("SearchResultFragment", "genreId: $genreId, searchQuery: $searchQuery")
        val data: List<Movie> = genreId?.let { genreQuery(it) } ?: titleQuery(searchQuery ?: "")
        Log.d("SearchResultFragment", "data size: ${data.size}")
        showResult(data)
    }

    private fun showResult(data: List<Movie>) {
        Log.d("showResult", "1")
        searchResultRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        searchResultRecyclerView.adapter = searchResultRecyclerViewAdapter(data)
        //searchResultRecyclerView.visibility = View.VISIBLE
        binding.backButton.visibility = View.VISIBLE
        /*
        searchResultRecyclerView.adapter = MovieAdapter(data) { movie ->
            val bundle = Bundle().apply { putParcelable("movie", movie) }
            findNavController().navigate(R.id.action_searchResult_to_movieDetail, bundle)
        }

         */
    }

    private fun titleQuery(titleWord: String): List<Movie>{
        return MovieData.filter{ movie ->
            movie.title.contains(titleWord, ignoreCase = true)
        }
    }

    private fun genreQuery(genreId: Int): List<Movie> {
        return MovieData.filter { movie ->
            movie.genre_ids.contains(genreId)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}