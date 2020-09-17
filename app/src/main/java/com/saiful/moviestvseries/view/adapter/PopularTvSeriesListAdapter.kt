package com.saiful.moviestvseries.view.adapter

import android.transition.Transition
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.saiful.moviestvseries.R
import com.saiful.moviestvseries.view.model.PopularMovies
import com.saiful.moviestvseries.view.model.PopularTVSeries
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.movie_list_item.view.*
import kotlinx.android.synthetic.main.movie_list_item.view.poster_image
import kotlinx.android.synthetic.main.tvseries_list_item.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.lang.System.load

@ExperimentalCoroutinesApi
class PopularTvSeriesListAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var serieslist : MutableList<PopularTVSeries.Result> = mutableListOf()
    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PopularTVSeries.Result>() {

        override fun areItemsTheSame(
            oldItem: PopularTVSeries.Result,
            newItem: PopularTVSeries.Result
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: PopularTVSeries.Result,
            newItem: PopularTVSeries.Result
        ): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return TVSeriesListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.tvseries_list_item,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TVSeriesListViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<PopularTVSeries.Result>) {
        serieslist.addAll(list)
        differ.submitList(serieslist)
    }

    class TVSeriesListViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: PopularTVSeries.Result) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            itemView.series_title.text = item.name
            itemView.on_air_date.text = item.firstAirDate

            Glide.with(context).load("http://image.tmdb.org/t/p/w185" + item.posterPath)
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .into(itemView.tv_poster_image)

            //itemView.animation = AnimationUtils.loadAnimation(itemView.context, R.anim.item_fade_in_down_to_up)

        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: PopularTVSeries.Result)
    }
}