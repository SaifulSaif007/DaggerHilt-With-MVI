package com.saiful.moviestvseries.services.network.mapper

import com.saiful.moviestvseries.view.model.PopularMovies
import com.saiful.moviestvseries.services.network.model.PopularMoviesNetworkEntity
import com.saiful.moviestvseries.util.EntityMapper
import javax.inject.Inject

class MovieNetworkMapper
@Inject constructor() :
    EntityMapper<PopularMoviesNetworkEntity.Result, PopularMovies.Result>
{
    override fun mapFromEntity(entity: PopularMoviesNetworkEntity.Result): PopularMovies.Result {
        return PopularMovies.Result(
            adult = entity.adult,
            backdropPath = entity.backdropPath,
            id = entity.id,
            originalLanguage = entity.originalLanguage,
            originalTitle = entity.originalTitle,
            overview = entity.overview,
            popularity = entity.popularity,
            posterPath = entity.posterPath,
            releaseDate = entity.releaseDate,
            title = entity.title,
            voteAverage = entity.voteAverage,
            voteCount = entity.voteCount
        )
    }


    fun mapFromEntityList(entities :  List<PopularMoviesNetworkEntity.Result>) : List<PopularMovies.Result>{
        return entities.map { mapFromEntity(it) }
    }


}