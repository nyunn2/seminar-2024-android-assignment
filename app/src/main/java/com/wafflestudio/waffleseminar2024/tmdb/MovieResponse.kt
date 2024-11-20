package com.wafflestudio.waffleseminar2024.tmdb

import com.wafflestudio.waffleseminar2024.Movie
import com.wafflestudio.waffleseminar2024.data.database.MyEntity

data class MovieResponse(
    val page: Int,
    val results: List<Movie>, // 영화 목록
    val total_pages: Int,
    val total_results: Int
)