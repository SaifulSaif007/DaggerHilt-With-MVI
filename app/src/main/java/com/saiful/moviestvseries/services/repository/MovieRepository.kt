package com.saiful.moviestvseries.services.repository

import android.util.Log
import com.saiful.moviestvseries.view.model.PopularMovies
import com.saiful.moviestvseries.services.network.abstraction.RetrofitService
import com.saiful.moviestvseries.services.network.mapper.MovieNetworkMapper
import com.saiful.moviestvseries.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class MovieRepository
constructor(
    private val retrofitService: RetrofitService,
    private val movieMapper : MovieNetworkMapper
) {

    suspend fun getPopularMovies() : Flow<DataState<List<PopularMovies.Result>>> = flow {
        emit(DataState.Loading)

        try {
            val networkMovies = retrofitService.getPopularMovies(
                "697bf3a9a65fafc6982838746d30694b",
                1
            )

            val movies = movieMapper.mapFromEntityList(networkMovies.results)
            emit(DataState.Success(movies))

        }
        catch (e: Exception){
            emit(DataState.Error(e))
        }
    }
}