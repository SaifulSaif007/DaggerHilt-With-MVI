package com.saiful.moviestvseries.services.network.abstraction

import com.saiful.moviestvseries.services.network.model.PopularMoviesNetworkEntity
import com.saiful.moviestvseries.services.network.model.PopularTVSeriesNetworkEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    //https://api.themoviedb.org/3/movie/popular?api_key=697bf3a9a65fafc6982838746d30694b&page=1

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") key : String,
        @Query("page") page : Int
    ) : PopularMoviesNetworkEntity

    //https://api.themoviedb.org/3/tv/popular?api_key=697bf3a9a65fafc6982838746d30694b&page=1

    @GET("tv/popular/")
    suspend fun getPopularTVSeries(
        @Query("api_key") key: String,
        @Query("page") page: Int
    ) : PopularTVSeriesNetworkEntity


}