package com.wafflestudio.waffleseminar2024.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.wafflestudio.waffleseminar2024.data.database.DbMovie
import com.wafflestudio.waffleseminar2024.data.database.LocalMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class LocalMovieViewModel @Inject constructor(
    private val localRepository: LocalMovieRepository
) : ViewModel() {
    val favoriteMovies: LiveData<List<DbMovie>> =
        localRepository.getAllFavorites().asLiveData()

    suspend fun addFavorite(movie: DbMovie) {
        viewModelScope.launch {
            localRepository.addFavorite(movie)
        }
    }

    suspend fun removeFavorite(movie: DbMovie) {
        viewModelScope.launch {
            localRepository.removeFavorite(movie)
        }
    }

    suspend fun isFavorite(movieId: Int): Boolean {
        return runBlocking { localRepository.isFavorite(movieId) }
    }
}
