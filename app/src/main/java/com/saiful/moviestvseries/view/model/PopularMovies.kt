package com.saiful.moviestvseries.view.model

data class PopularMovies(
    val page: Int,
    val results: List<PopularMovies.Result>,
    val totalPages: Int,
    val totalResults: Int
){
    data class Result(
        val adult: Boolean?=null,
        val backdropPath: String? = null,
        val id: Int,
        val originalLanguage: String?=null,
        val originalTitle: String?=null,
        val overview: String?=null,
        val popularity: Double?=null,
        val posterPath: String? = null,
        val releaseDate: String?=null,
        val title: String?=null,
        val voteAverage: Double?=null,
        val voteCount: Int?=null
    )
}