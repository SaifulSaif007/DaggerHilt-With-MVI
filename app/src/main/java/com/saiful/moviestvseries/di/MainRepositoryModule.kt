package com.saiful.moviestvseries.di

import com.saiful.moviestvseries.services.network.abstraction.RetrofitService
import com.saiful.moviestvseries.services.network.mapper.MovieDetailsNetworkMapper
import com.saiful.moviestvseries.services.network.mapper.PopularMoviesNetworkMapper
import com.saiful.moviestvseries.services.network.mapper.PopularTVSeriesNetworkMapper
import com.saiful.moviestvseries.services.network.mapper.SeriesDetailsNetworkMapper
import com.saiful.moviestvseries.services.repository.MovieRepository
import com.saiful.moviestvseries.services.repository.TVSeriesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
@ExperimentalCoroutinesApi
object MainRepositoryModule {

    @Singleton
    @Provides
    fun provideMovieRepo(
        retrofit : RetrofitService,
        popularMoviesMapper : PopularMoviesNetworkMapper,
        movieDetailsMapper : MovieDetailsNetworkMapper
    ) : MovieRepository {
        return MovieRepository(retrofit, popularMoviesMapper, movieDetailsMapper)
    }


    @Singleton
    @Provides
    fun provideTvSeriesRepo(
        retrofit: RetrofitService,
        popularTvSeriesMapper : PopularTVSeriesNetworkMapper,
        seriesDetailsNetworkMapper: SeriesDetailsNetworkMapper
    ) : TVSeriesRepository {
        return TVSeriesRepository(retrofit, popularTvSeriesMapper, seriesDetailsNetworkMapper)
    }

}