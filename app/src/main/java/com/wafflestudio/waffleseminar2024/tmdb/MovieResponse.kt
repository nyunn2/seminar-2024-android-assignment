package com.wafflestudio.waffleseminar2024.tmdb

import com.wafflestudio.waffleseminar2024.data.database.MyEntity

data class MovieResponse(
    val page: Int,
    val results: List<MyEntity>, // 영화 목록
    val total_pages: Int,
    val total_results: Int
)