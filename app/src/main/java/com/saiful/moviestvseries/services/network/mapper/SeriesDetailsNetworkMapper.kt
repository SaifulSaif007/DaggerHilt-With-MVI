package com.saiful.moviestvseries.services.network.mapper

import com.saiful.moviestvseries.services.network.model.SeriesDetailsNetworkEntity
import com.saiful.moviestvseries.util.EntityMapper
import com.saiful.moviestvseries.view.model.SeriesDetails
import javax.inject.Inject

class SeriesDetailsNetworkMapper
@Inject constructor() :
    EntityMapper<SeriesDetailsNetworkEntity, SeriesDetails> {

    override fun mapFromEntity(entity: SeriesDetailsNetworkEntity): SeriesDetails {
        return SeriesDetails(
            backdropPath = entity.backdropPath,
            firstAirDate = entity.firstAirDate,
            genres = entity.genres,
            id = entity.id,
            lastAirDate = entity.lastAirDate,
            lastEpisodeToAir = entity.lastEpisodeToAir,
            name = entity.name,
            nextEpisodeToAir = entity.nextEpisodeToAir,
            numberOfEpisodes = entity.numberOfEpisodes,
            numberOfSeasons = entity.numberOfSeasons,
            originalName = entity.originalName,
            overview = entity.overview,
            posterPath = entity.posterPath,
            seasons = entity.seasons,
            status = entity.status,
            type = entity.type,
            voteAverage = entity.voteAverage,
            voteCount = entity.voteCount,
            videos = entity.videos

        )
    }
}