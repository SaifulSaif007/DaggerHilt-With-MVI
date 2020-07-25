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
import com.saiful.moviestvseries.databinding.FragmentTvSeriesBinding
import com.saiful.moviestvseries.util.DataState
import com.saiful.moviestvseries.view.model.PopularTVSeries
import com.saiful.moviestvseries.view.viewModel.MainStateEvent
import com.saiful.moviestvseries.view.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class TVSeriesFragment : Fragment() {

    private var _binding : FragmentTvSeriesBinding? = null
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
        viewModel.setStateEvent(MainStateEvent.GetPopularTVSeries)

        _binding = FragmentTvSeriesBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    private fun subscribeObserver() {
        viewModel.dataStateForTVSeries.observe(viewLifecycleOwner, Observer { dataState ->
            when(dataState){
                is DataState.Success<PopularTVSeries> -> {
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