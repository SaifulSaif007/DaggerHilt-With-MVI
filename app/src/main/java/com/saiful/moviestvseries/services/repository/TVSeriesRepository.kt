package com.saiful.moviestvseries.services.repository


import com.saiful.moviestvseries.services.network.abstraction.RetrofitService
import com.saiful.moviestvseries.services.network.mapper.PopularTVSeriesNetworkMapper
import com.saiful.moviestvseries.services.network.mapper.SeriesDetailsNetworkMapper
import com.saiful.moviestvseries.util.DataState
import com.saiful.moviestvseries.view.adapter.TvSeriesPaginationListner
import com.saiful.moviestvseries.view.adapter.TvSeriesPaginationListner.Companion.PAGE_START
import com.saiful.moviestvseries.view.adapter.TvSeriesPaginationListner.Companion.QUERY
import com.saiful.moviestvseries.view.model.PopularTVSeries
import com.saiful.moviestvseries.view.model.SeriesDetails
import com.saiful.moviestvseries.view.ui.SeriesDetailsFragment.Companion.SeriesId
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

@ExperimentalCoroutinesApi
class TVSeriesRepository
constructor(
    private val retrofitService: RetrofitService,
    private val popularTvSeriesMapper: PopularTVSeriesNetworkMapper,
    private val seriesDetailsNetworkMapper: SeriesDetailsNetworkMapper
){

    suspend fun getPopularTvSeries() : Flow<DataState<List<PopularTVSeries.Result>>> = flow {
        emit(DataState.Loading)

        try {
            val networkTVSeries = retrofitService.getPopularTVSeries(
                PAGE_START
            )

            val tvSeries = popularTvSeriesMapper.mapFromEntityList(networkTVSeries.results)
            emit(DataState.Success(tvSeries))
        }
        catch (e : Exception){
            emit(DataState.Error(e))
        }
    }


    suspend fun getSearchedSeries() : Flow<DataState<List<PopularTVSeries.Result>>> = flow {
        emit(DataState.Loading)

        try {
            val networkTVSeries = retrofitService.getSearchedSeries(
                PAGE_START,
                QUERY
            )

            val tvSeries = popularTvSeriesMapper.mapFromEntityList(networkTVSeries.results)
            emit(DataState.Success(tvSeries))
        }
        catch (e : Exception){
            emit(DataState.Error(e))
        }
    }

    suspend fun getSeriesDetails() : Flow<DataState<SeriesDetails>> = flow {
        emit(DataState.Loading)

        try {
            val networkDetails = retrofitService.getSeriesDetails(
                SeriesId,
                "videos"
            )

            val details = seriesDetailsNetworkMapper.mapFromEntity(networkDetails)
            emit(DataState.Success(details))
        }
        catch (e : Exception){
            emit(DataState.Error(e))
        }
    }
}