package com.saiful.moviestvseries.view.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.saiful.moviestvseries.R
import com.saiful.moviestvseries.databinding.FragmentMoviesBinding
import com.saiful.moviestvseries.util.DataState
import com.saiful.moviestvseries.view.model.PopularMovies
import com.saiful.moviestvseries.view.viewModel.MainStateEvent
import com.saiful.moviestvseries.view.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private var _binding : FragmentMoviesBinding? = null
    private val binding get() = _binding!!

    private val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        subscribeObserver()
        viewModel.setStateEvent(MainStateEvent.GetPopularMovies)

        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return  binding.root

    }

    private fun subscribeObserver() {
        viewModel.dataStateForMovies.observe(viewLifecycleOwner, Observer { dataState ->
            when(dataState){
                is DataState.Success<PopularMovies> -> {
                    Log.e("Check", "" +  dataState.data.totalResults)
                }
                is DataState.Error -> {
                    Log.e("Check",  dataState.Exception.message)
                }
                is DataState.Loading -> {
                    Log.e("loading", "Loading")
                }
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}