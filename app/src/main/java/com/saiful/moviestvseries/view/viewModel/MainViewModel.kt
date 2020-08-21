package com.saiful.moviestvseries.view.viewModel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.saiful.moviestvseries.services.repository.MovieRepository
import com.saiful.moviestvseries.services.repository.TVSeriesRepository
import com.saiful.moviestvseries.util.DataState
import com.saiful.moviestvseries.view.model.MovieDetails
import com.saiful.moviestvseries.view.model.PopularMovies
import com.saiful.moviestvseries.view.model.PopularTVSeries
import com.saiful.moviestvseries.view.model.SeriesDetails
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainViewModel
@ViewModelInject
constructor(
    private val movieRepository: MovieRepository,
    private val tvSeriesRepository: TVSeriesRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private var _dataStateForMovies : MutableLiveData<DataState<List<PopularMovies.Result>>> = MutableLiveData()
    private var _dataStateForTVSeries : MutableLiveData<DataState<List<PopularTVSeries.Result>>> = MutableLiveData()
    private var _dataStateMovieDetails : MutableLiveData<DataState<MovieDetails>> = MutableLiveData()
    private var _dataStateSeriesDetails : MutableLiveData<DataState<SeriesDetails>> = MutableLiveData()

    val dataStateForMovies : LiveData<DataState<List<PopularMovies.Result>>>
            get() = _dataStateForMovies

    val dataStateForTVSeries : LiveData<DataState<List<PopularTVSeries.Result>>>
            get() = _dataStateForTVSeries

    val dataStateMovieDetails : LiveData<DataState<MovieDetails>>
            get() = _dataStateMovieDetails

    val dataStateSeriesDetails : LiveData<DataState<SeriesDetails>>
            get() = _dataStateSeriesDetails


    fun setStateEvent(mainStateEvent: MainStateEvent){
        viewModelScope.launch {
            when(mainStateEvent){
                is MainStateEvent.GetPopularMovies -> {
                    movieRepository.getPopularMovies()
                        .onEach {dataState ->
                                _dataStateForMovies.value = dataState
                        }
                        .launchIn(viewModelScope)
                }

                is MainStateEvent.GetPopularTVSeries -> {
                    tvSeriesRepository.getPopularTvSeries()
                        .onEach { dataState ->
                            _dataStateForTVSeries.value = dataState
                        }
                        .launchIn(viewModelScope)
                }

                is MainStateEvent.GetMovieDetails -> {
                    movieRepository.getMovieDetails()
                        .onEach {dataState ->
                            _dataStateMovieDetails.value = dataState
                        }
                        .launchIn(viewModelScope)
                }

                is MainStateEvent.GetSeriesDetails -> {
                    tvSeriesRepository.getSeriesDetails()
                        .onEach { dataState ->
                            _dataStateSeriesDetails.value = dataState
                        }
                        .launchIn(viewModelScope)
                }

                is MainStateEvent.None -> {
                    // Nothing Now
                }
            }
        }
    }
}


sealed class MainStateEvent() {

    object GetPopularMovies : MainStateEvent()

    object GetPopularTVSeries : MainStateEvent()

    class GetMovieDetails() : MainStateEvent()

    object GetSeriesDetails : MainStateEvent()

    object None : MainStateEvent()

}