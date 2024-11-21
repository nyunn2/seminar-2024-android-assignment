package com.wafflestudio.waffleseminar2024.data.database

import android.util.Log
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalMovieRepository @Inject constructor(
    private val myDao: MyDao
) {
    suspend fun addFavorite(movie: DbMovie) {
        myDao.insertMovie(movie)
    }

    suspend fun removeFavorite(movie: DbMovie) {
        myDao.deleteMovie(movie)
    }

    fun getAllFavorites(): Flow<List<DbMovie>> {
        val favorites = myDao.getAllMovies()
        Log.d("LocalMovieRepository", "Database favorites: $favorites") // 데이터베이스 상태 출력
        return favorites
    }

    suspend fun isFavorite(movieId: Int): Boolean {
        return myDao.getMovieById(movieId) != null
    }
}
