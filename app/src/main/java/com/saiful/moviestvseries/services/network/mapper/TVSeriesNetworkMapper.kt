package com.saiful.moviestvseries.services.network.mapper

import com.saiful.moviestvseries.view.model.PopularTVSeries
import com.saiful.moviestvseries.services.network.model.PopularTVSeriesNetworkEntity
import com.saiful.moviestvseries.util.EntityMapper
import javax.inject.Inject

class TVSeriesNetworkMapper
@Inject constructor() :
    EntityMapper<PopularTVSeriesNetworkEntity.Result, PopularTVSeries.Result>
{
    override fun mapFromEntity(entity: PopularTVSeriesNetworkEntity.Result): PopularTVSeries.Result {
        return PopularTVSeries.Result(
            backdropPath = entity.backdropPath,
            firstAirDate = entity.firstAirDate,
            id = entity.id,
            name = entity.name,
            originalLanguage = entity.originalLanguage,
            overview = entity.overview,
            popularity = entity.popularity,
            posterPath = entity.posterPath,
            voteAverage = entity.voteAverage,
            voteCount = entity.voteCount
        )
    }


    fun mapFromEntityList(entities : List<PopularTVSeriesNetworkEntity.Result>) : List<PopularTVSeries.Result>{
        return entities.map { mapFromEntity(it) }
    }


}