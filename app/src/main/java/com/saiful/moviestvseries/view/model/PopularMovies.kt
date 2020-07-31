package com.saiful.moviestvseries.view.model

data class PopularMovies(
    val page: Int,
    val results: List<PopularMovies.Result>,
    val totalPages: Int,
    val totalResults: Int
){
    data class Result(
        val adult: Boolean,
        val backdropPath: String,
        val id: Int,
        val originalLanguage: String,
        val originalTitle: String,
        val overview: String,
        val popularity: Double,
        val posterPath: String,
        val releaseDate: String,
        val title: String,
        val voteAverage: Double,
        val voteCount: Int
    )
}