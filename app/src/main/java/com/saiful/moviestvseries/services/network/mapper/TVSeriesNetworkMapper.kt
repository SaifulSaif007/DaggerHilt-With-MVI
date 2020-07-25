package com.saiful.moviestvseries.services.network.mapper

import com.saiful.moviestvseries.view.model.PopularTVSeries
import com.saiful.moviestvseries.services.network.model.PopularTVSeriesNetworkEntity
import com.saiful.moviestvseries.util.EntityMapper
import javax.inject.Inject

class TVSeriesNetworkMapper
@Inject constructor() :
    EntityMapper<PopularTVSeriesNetworkEntity, PopularTVSeries>
{
    override fun mapFromEntity(entity: PopularTVSeriesNetworkEntity): PopularTVSeries {
        return PopularTVSeries(
            page = entity.page,
            results = entity.results,
            totalResults = entity.totalResults,
            totalPages = entity.totalPages
        )
    }


    fun mapFromEntityList(entities : List<PopularTVSeriesNetworkEntity>) : List<PopularTVSeries>{
        return entities.map { mapFromEntity(it) }
    }


}