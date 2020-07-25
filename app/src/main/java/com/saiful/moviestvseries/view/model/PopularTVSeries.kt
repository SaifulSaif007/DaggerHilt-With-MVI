package com.saiful.moviestvseries.view.model

import com.saiful.moviestvseries.services.network.model.PopularTVSeriesNetworkEntity

data class PopularTVSeries (
    val page: Int,
    val results: List<PopularTVSeriesNetworkEntity.Result>,
    val totalPages: Int,
    val totalResults: Int
) {
    data class Result(
        val backdropPath: String,
        val firstAirDate: String,
        val genreIds: List<Int>,
        val id: Int,
        val name: String,
        val originCountry: List<String>,
        val originalLanguage: String,
        val originalName: String,
        val overview: String,
        val popularity: Double,
        val posterPath: String,
        val voteAverage: Double,
        val voteCount: Int
    )
}