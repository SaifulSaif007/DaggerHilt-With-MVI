package com.saiful.moviestvseries.view.viewModel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.saiful.moviestvseries.services.repository.MovieRepository
import com.saiful.moviestvseries.services.repository.TVSeriesRepository
import com.saiful.moviestvseries.util.DataState
import com.saiful.moviestvseries.view.model.PopularMovies
import com.saiful.moviestvseries.view.model.PopularTVSeries
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

    private val _dataStateForMovies : MutableLiveData<DataState<List<PopularMovies.Result>>> = MutableLiveData()
    private val _dataStateForTVSeries : MutableLiveData<DataState<List<PopularTVSeries.Result>>> = MutableLiveData()

    val dataStateForMovies : LiveData<DataState<List<PopularMovies.Result>>>
            get() = _dataStateForMovies

    val dataStateForTVSeries : LiveData<DataState<List<PopularTVSeries.Result>>>
            get() = _dataStateForTVSeries


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

                is MainStateEvent.None -> {
                    // Nothing Now
                }
            }
        }
    }
}

sealed class MainStateEvent {

    object GetPopularMovies : MainStateEvent()

    object GetPopularTVSeries : MainStateEvent()

    object None : MainStateEvent()

}