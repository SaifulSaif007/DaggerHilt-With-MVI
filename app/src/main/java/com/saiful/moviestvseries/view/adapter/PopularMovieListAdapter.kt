package com.saiful.moviestvseries.view.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.saiful.moviestvseries.R
import com.saiful.moviestvseries.view.model.PopularMovies
import com.saiful.moviestvseries.view.ui.MoviesFragment
import kotlinx.android.synthetic.main.movie_list_item.view.*

class PopularMovieListAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PopularMovies.Result>() {

        override fun areItemsTheSame(
            oldItem: PopularMovies.Result,
            newItem: PopularMovies.Result
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: PopularMovies.Result,
            newItem: PopularMovies.Result
        ): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return PopularMovieViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.movie_list_item,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PopularMovieViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<PopularMovies.Result>) {
        differ.submitList(list)
    }

    class PopularMovieViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: PopularMovies.Result) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            itemView.movie_title.text = item.title
            itemView.release_date.text = item.releaseDate

            Glide.with(itemView.context)
                .load("http://image.tmdb.org/t/p/w185" + item.posterPath)
                .into(itemView.poster_image)
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: PopularMovies.Result)
    }
}