package com.saiful.moviestvseries.di

import com.saiful.moviestvseries.services.network.abstraction.RetrofitService
import com.saiful.moviestvseries.services.network.mapper.MovieNetworkMapper
import com.saiful.moviestvseries.services.network.mapper.TVSeriesNetworkMapper
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
        movieMapper : MovieNetworkMapper
    ) : MovieRepository {
        return MovieRepository(retrofit, movieMapper)
    }

    @Singleton
    @Provides
    fun provideTvSeriesRepo(
        retrofit: RetrofitService,
        tvSeriesMapper : TVSeriesNetworkMapper
    ) : TVSeriesRepository {
        return TVSeriesRepository(retrofit, tvSeriesMapper)
    }

}