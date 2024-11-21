package com.wafflestudio.waffleseminar2024.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.wafflestudio.waffleseminar2024.R
import com.wafflestudio.waffleseminar2024.data.database.DbMovie

class FavoriteMoviesAdapter(
    private val onItemClick: (DbMovie) -> Unit
) : ListAdapter<DbMovie, FavoriteMoviesAdapter.MovieViewHolder>(DiffCallback()) {

    inner class MovieViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemImageView: ImageView = itemView.findViewById(R.id.itemImageView)

        fun bind(movie: DbMovie) {
            itemImageView.load("https://image.tmdb.org/t/p/original${movie.posterPath}")

            itemView.setOnClickListener {
                onItemClick(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_favorite_movie,
            parent,
            false
        )
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        Log.d("FavoriteMoviesAdapter", "Binding movie: $movie")
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<DbMovie>() {
        override fun areItemsTheSame(oldItem: DbMovie, newItem: DbMovie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DbMovie, newItem: DbMovie): Boolean {
            return oldItem == newItem
        }
    }
}
