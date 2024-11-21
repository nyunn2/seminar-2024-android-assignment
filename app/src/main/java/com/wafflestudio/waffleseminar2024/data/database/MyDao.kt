package com.wafflestudio.waffleseminar2024.data.database

import android.util.Log
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wafflestudio.waffleseminar2024.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: DbMovie)

    @Delete
    suspend fun deleteMovie(movie: DbMovie)

    @Query("SELECT * FROM example_table2")
    fun getAllMovies(): Flow<List<DbMovie>>

    @Query("SELECT * FROM example_table2 WHERE id = :movieId")
    suspend fun getMovieById(movieId: Int): DbMovie?
}