package com.wafflestudio.waffleseminar2024.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wafflestudio.waffleseminar2024.Movie
import com.wafflestudio.waffleseminar2024.data.database.MovieRepository
import com.wafflestudio.waffleseminar2024.data.database.MyEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies

    private val _movieDetail = MutableLiveData<MyEntity>()
    val movieDetail: LiveData<MyEntity> get() = _movieDetail

    fun fetchMovieDetails(id: Int) {
        viewModelScope.launch {
            try {
                Log.d("MovieViewModel", "Fetching movie details for ID: $id")
                val movieDetail = repository.getMovieById(id)
                _movieDetail.postValue(movieDetail)
                Log.d("MovieViewModel", "Fetched movie details: $movieDetail")
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Error fetching movie details for ID: $id", e)
                e.printStackTrace()
            }
        }
    }

    fun searchMovies(query: String) {
        viewModelScope.launch {
            try {
                val movies = repository.getMoviesByTitle(query)
                _movies.postValue(movies)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getMoviesByGenre(genreId: Int) {
        viewModelScope.launch {
            try {
                val movies = repository.getMoviesByGenre(genreId)
                _movies.postValue(movies)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}