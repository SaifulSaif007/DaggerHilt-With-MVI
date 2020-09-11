package com.saiful.moviestvseries.view.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.saiful.moviestvseries.R
import com.saiful.moviestvseries.services.network.model.SeriesDetailsNetworkEntity
import kotlinx.android.synthetic.main.trailer_list_item.view.*

class SeriesTrailerAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SeriesDetailsNetworkEntity.VideoResult>() {

        override fun areItemsTheSame(
            oldItem: SeriesDetailsNetworkEntity.VideoResult,
            newItem: SeriesDetailsNetworkEntity.VideoResult
        ): Boolean {
            return oldItem.video_id == newItem.video_id
        }

        override fun areContentsTheSame(
            oldItem: SeriesDetailsNetworkEntity.VideoResult,
            newItem: SeriesDetailsNetworkEntity.VideoResult
        ): Boolean {
            return oldItem.video_id == newItem.video_id
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return TrailerViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.trailer_list_item,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TrailerViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<SeriesDetailsNetworkEntity.VideoResult>) {
        differ.submitList(list)
    }

    class TrailerViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: SeriesDetailsNetworkEntity.VideoResult) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            itemView.trailer_name.text = item.name
            val imageUrl = "https://img.youtube.com/vi/" + item.key +"/0.jpg"

            Glide.with(itemView.context)
                .load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .into(itemView.trailer_image)

        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: SeriesDetailsNetworkEntity.VideoResult)
    }
}