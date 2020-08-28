package com.saiful.moviestvseries.view.model

import com.saiful.moviestvseries.services.network.model.SeriesDetailsNetworkEntity

class SeriesDetails (
    val backdropPath: String? = null,
    val firstAirDate: String? = null,
    val genres: List<SeriesDetailsNetworkEntity.Genre?>? = null,
    val id: Int? = null,
    val lastAirDate: String? = null,
    val lastEpisodeToAir: SeriesDetailsNetworkEntity.LastEpisodeToAir? = null,
    val name: String? = null,
    val nextEpisodeToAir: Any? = null,
    val numberOfEpisodes: Int? = null,
    val numberOfSeasons: Int? = null,
    val originalName: String? = null,
    val overview: String? = null,
    val posterPath: String? = null,
    val seasons: List<SeriesDetailsNetworkEntity.Season?>? = null,
    val status: String? = null,
    val type: String? = null,
    val voteAverage: Double? = null,
    val voteCount: Int? = null
)