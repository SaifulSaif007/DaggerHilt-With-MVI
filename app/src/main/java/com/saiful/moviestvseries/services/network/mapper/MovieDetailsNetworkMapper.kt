package com.saiful.moviestvseries.services.network.mapper

import com.saiful.moviestvseries.services.network.model.MovieDetailsNetworkEntity
import com.saiful.moviestvseries.util.EntityMapper
import com.saiful.moviestvseries.view.model.MovieDetails
import javax.inject.Inject

class MovieDetailsNetworkMapper
@Inject constructor() : EntityMapper<MovieDetailsNetworkEntity, MovieDetails>{

    override fun mapFromEntity(entity: MovieDetailsNetworkEntity): MovieDetails {
       return MovieDetails(
           backdropPath = entity.backdropPath,
           budget = entity.budget,
           genres = entity.genres,
           spokenLanguages = entity.spokenLanguages,
           id = entity.id,
           originalTitle = entity.originalTitle,
           overview = entity.overview,
           posterPath = entity.posterPath,
           releaseDate = entity.releaseDate,
           revenue = entity.revenue,
           status = entity.status,
           tagline = entity.tagline,
           title = entity.title,
           voteAverage = entity.voteAverage,
           voteCount = entity.voteCount,
           videos = entity.videos
       )
    }


}