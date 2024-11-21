package com.wafflestudio.waffleseminar2024.data.database

import com.wafflestudio.waffleseminar2024.Movie
import com.wafflestudio.waffleseminar2024.tmdb.TMDBApiService
import javax.inject.Inject
import javax.inject.Singleton

class MovieRepository @Inject constructor(
    private val apiService: TMDBApiService
) {
    suspend fun getMovieById(id: Int): MyEntity {
        return apiService.getMovieDetails(movieId = id)
    }

    suspend fun getMoviesByTitle(titleWord: String): List<Movie> {
        val response = apiService.searchMovies(query = titleWord)
        return response.results
    }

    suspend fun getMoviesByGenre(genreId: Int): List<Movie> {
        val response = apiService.getMoviesByGenre(genreId = genreId)
        return response.results
    }
}

