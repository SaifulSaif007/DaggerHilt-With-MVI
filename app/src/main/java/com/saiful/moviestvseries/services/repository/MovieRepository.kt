package com.saiful.moviestvseries.services.repository

import com.saiful.moviestvseries.view.model.PopularMovies
import com.saiful.moviestvseries.services.network.abstraction.RetrofitService
import com.saiful.moviestvseries.services.network.mapper.MovieDetailsNetworkMapper
import com.saiful.moviestvseries.services.network.mapper.PopularMoviesNetworkMapper
import com.saiful.moviestvseries.util.DataState
import com.saiful.moviestvseries.view.adapter.MoviePaginationListner.Companion.PAGE_START
import com.saiful.moviestvseries.view.model.MovieDetails
import com.saiful.moviestvseries.view.ui.MovieDetailsFragment.Companion.MovieId
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

@ExperimentalCoroutinesApi
class MovieRepository
constructor(
    private val retrofitService: RetrofitService,
    private val popularMoviesMapper : PopularMoviesNetworkMapper,
    private val movieDetailsMapper : MovieDetailsNetworkMapper
) {

    suspend fun getPopularMovies() : Flow<DataState<List<PopularMovies.Result>>> = flow {
        emit(DataState.Loading)

        try {
            val networkMovies = retrofitService.getPopularMovies(
                "697bf3a9a65fafc6982838746d30694b",
                PAGE_START
            )

            val movies = popularMoviesMapper.mapFromEntityList(networkMovies.results)
            emit(DataState.Success(movies))

        }
        catch (e: Exception){
            emit(DataState.Error(e))
        }
    }


    suspend fun getMovieDetails() : Flow<DataState<MovieDetails>> = flow {
        emit(DataState.Loading)

        try {
            val networkDetails = retrofitService.getMovieDetails(
                MovieId,
                "697bf3a9a65fafc6982838746d30694b",
                "videos"
            )

            val details = movieDetailsMapper.mapFromEntity(networkDetails)
            emit(DataState.Success(details))

        }
        catch (e : Exception){
            emit(DataState.Error(e))
        }
    }
}