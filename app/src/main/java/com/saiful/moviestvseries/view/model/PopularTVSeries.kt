package com.saiful.moviestvseries.view.model

data class PopularTVSeries (
    val page: Int,
    val results: List<PopularTVSeries.Result>,
    val totalPages: Int,
    val totalResults: Int
) {
    data class Result(
        val backdropPath: String?=null,
        val firstAirDate: String?=null,
        val id: Int,
        val name: String?=null,
        val originalLanguage: String?=null,
        val overview: String?=null,
        val popularity: Double?=null,
        val posterPath: String?=null,
        val voteAverage: Double?=null,
        val voteCount: Int?=null
    )
}