package com.saiful.moviestvseries.services.repository

import com.saiful.moviestvseries.services.network.abstraction.RetrofitService
import com.saiful.moviestvseries.services.network.mapper.TVSeriesNetworkMapper
import com.saiful.moviestvseries.util.DataState
import com.saiful.moviestvseries.view.model.PopularTVSeries
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class TVSeriesRepository
constructor(
    private val retrofitService: RetrofitService,
    private val tvSeriesMapper: TVSeriesNetworkMapper
){

    suspend fun getPopularTvSeries() : Flow<DataState<List<PopularTVSeries.Result>>> = flow {
        emit(DataState.Loading)

        try {
            val networkTVSeries = retrofitService.getPopularTVSeries(
                "697bf3a9a65fafc6982838746d30694b",
                1
            )

            val tvSeries =tvSeriesMapper.mapFromEntityList(networkTVSeries.results)
            emit(DataState.Success(tvSeries))
        }
        catch (e : Exception){
            emit(DataState.Error(e))
        }
    }
}