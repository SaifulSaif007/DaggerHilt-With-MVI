package com.saiful.moviestvseries.services.network.mapper

import com.saiful.moviestvseries.view.model.PopularMovies
import com.saiful.moviestvseries.services.network.model.PopularMoviesNetworkEntity
import com.saiful.moviestvseries.util.EntityMapper
import javax.inject.Inject

class MovieNetworkMapper
@Inject constructor() :
    EntityMapper<PopularMoviesNetworkEntity, PopularMovies>
{
    override fun mapFromEntity(entity: PopularMoviesNetworkEntity): PopularMovies {
        return PopularMovies(
            page =  entity.page,
            results = entity.results,
            totalPages = entity.totalPages,
            totalResults = entity.totalResults
        )
    }



    fun mapFromEntityList(entities :  List<PopularMoviesNetworkEntity>) : List<PopularMovies>{
        return entities.map { mapFromEntity(it) }
    }

}