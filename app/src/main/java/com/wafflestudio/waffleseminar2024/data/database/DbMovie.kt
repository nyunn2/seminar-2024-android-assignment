package com.wafflestudio.waffleseminar2024.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "example_table2")
data class DbMovie(
    @PrimaryKey val id: Int?,
    val title: String?,
    val posterPath: String?
)
