package com.saiful.moviestvseries.view.model

import com.saiful.moviestvseries.services.network.model.PopularMoviesNetworkEntity

data class PopularMovies(
    val page: Int,
    val results: List<PopularMoviesNetworkEntity.Result>,
    val totalPages: Int,
    val totalResults: Int
){
    data class Result(
        val adult: Boolean,
        val backdropPath: String,
        val genreIds: List<Int>,
        val id: Int,
        val originalLanguage: String,
        val originalTitle: String,
        val overview: String,
        val popularity: Double,
        val posterPath: String,
        val releaseDate: String,
        val title: String,
        val video: Boolean,
        val voteAverage: Double,
        val voteCount: Int
    )
}