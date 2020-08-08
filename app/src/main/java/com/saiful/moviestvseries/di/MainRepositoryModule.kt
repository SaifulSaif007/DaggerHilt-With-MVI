package com.saiful.moviestvseries.di

import com.saiful.moviestvseries.services.network.abstraction.RetrofitService
import com.saiful.moviestvseries.services.network.mapper.PopularMoviesNetworkMapper
import com.saiful.moviestvseries.services.network.mapper.PopularTVSeriesNetworkMapper
import com.saiful.moviestvseries.services.repository.MovieRepository
import com.saiful.moviestvseries.services.repository.TVSeriesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton


@InstallIn(ApplicationComponent::class)
@Module
object MainRepositoryModule {

    @Singleton
    @Provides
    fun provideMovieRepo(
        retrofit : RetrofitService,
        popularMoviesMapper : PopularMoviesNetworkMapper
    ) : MovieRepository {
        return MovieRepository(retrofit, popularMoviesMapper)
    }

    @Singleton
    @Provides
    fun provideTvSeriesRepo(
        retrofit: RetrofitService,
        popularTvSeriesMapper : PopularTVSeriesNetworkMapper
    ) : TVSeriesRepository {
        return TVSeriesRepository(retrofit, popularTvSeriesMapper)
    }

}