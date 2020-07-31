package com.saiful.moviestvseries.view.model

data class PopularTVSeries (
    val page: Int,
    val results: List<PopularTVSeries.Result>,
    val totalPages: Int,
    val totalResults: Int
) {
    data class Result(
        val backdropPath: String,
        val firstAirDate: String,
        val id: Int,
        val name: String,
        val originalLanguage: String,
        val overview: String,
        val popularity: Double,
        val posterPath: String,
        val voteAverage: Double,
        val voteCount: Int
    )
}