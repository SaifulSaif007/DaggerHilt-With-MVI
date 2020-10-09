package com.saiful.moviestvseries.services.network.abstraction

import com.saiful.moviestvseries.BuildConfig
import com.saiful.moviestvseries.services.network.model.MovieDetailsNetworkEntity
import com.saiful.moviestvseries.services.network.model.PopularMoviesNetworkEntity
import com.saiful.moviestvseries.services.network.model.PopularTVSeriesNetworkEntity
import com.saiful.moviestvseries.services.network.model.SeriesDetailsNetworkEntity
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    companion object
    {
        const val API_KEY =  BuildConfig.API_ACCESS_KEY
    }

    @GET("movie/popular?api_key=$API_KEY")
    suspend fun getPopularMovies(
        @Query("page") page : Int
    ) : PopularMoviesNetworkEntity


    @GET("search/movie?api_key=$API_KEY")
    suspend fun getSearchedMovies(
        @Query("query") query: String,
        @Query("page") page : Int
    ) : PopularMoviesNetworkEntity


    @GET("tv/popular?api_key=$API_KEY")
    suspend fun getPopularTVSeries(
        @Query("page") page: Int
    ) : PopularTVSeriesNetworkEntity


    @GET("search/tv?api_key=$API_KEY")
    suspend fun getSearchedSeries(
        @Query("page") page: Int,
        @Query("query") query: String
    ) : PopularTVSeriesNetworkEntity


    @GET("movie/{id}?api_key=$API_KEY")
    suspend fun getMovieDetails(
        @Path("id") id: Int,
        @Query("append_to_response") response: String
    ) : MovieDetailsNetworkEntity


    @GET("tv/{id}?api_key=$API_KEY")
    suspend fun getSeriesDetails(
        @Path("id") id: Int,
        @Query("append_to_response") response: String
    ) : SeriesDetailsNetworkEntity

}