package com.saiful.moviestvseries.view.model

import com.saiful.moviestvseries.services.network.model.MovieDetailsNetworkEntity

data class MovieDetails(
    val backdropPath: String? = null,
    val budget: Int? = null,
    val genres: List<MovieDetailsNetworkEntity.Genre?>? = null,
    val id: Int? = null,
    val originalTitle: String? = null,
    val overview: String? = null,
    val posterPath: String? = null,
    val releaseDate: String? = null,
    val revenue: Double? = null,
    val spokenLanguages: List<MovieDetailsNetworkEntity.SpokenLanguage?>? = null,
    val status: String? = null,
    val tagline: String? = null,
    val title: String? = null,
    val voteAverage: Double? = null,
    val voteCount: Int? = null,
    val videos : MovieDetailsNetworkEntity.Videos? = null
    )