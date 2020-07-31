package com.saiful.moviestvseries.view.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.saiful.moviestvseries.databinding.FragmentTvSeriesBinding
import com.saiful.moviestvseries.util.DataState
import com.saiful.moviestvseries.util.ItemDecorator
import com.saiful.moviestvseries.view.adapter.PopularMovieListAdapter
import com.saiful.moviestvseries.view.adapter.PopularTvSeriesListAdapter
import com.saiful.moviestvseries.view.model.PopularTVSeries
import com.saiful.moviestvseries.view.viewModel.MainStateEvent
import com.saiful.moviestvseries.view.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class TVSeriesFragment : Fragment(), PopularTvSeriesListAdapter.Interaction {

    private var _binding : FragmentTvSeriesBinding? = null
    private val binding get() = _binding!!

    private val viewModel : MainViewModel by viewModels()

    lateinit var tvSeriesListAdapter : PopularTvSeriesListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTvSeriesBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeObserver()
        viewModel.setStateEvent(MainStateEvent.GetPopularTVSeries)
        initRecycler()
    }

    private fun initRecycler(){
        binding.recyclerview.apply {
            layoutManager = GridLayoutManager(activity, 3)
            val itemDecorator = ItemDecorator()
            addItemDecoration(itemDecorator)
            tvSeriesListAdapter = PopularTvSeriesListAdapter(this@TVSeriesFragment)
            adapter = tvSeriesListAdapter
        }
    }

    private fun subscribeObserver() {
        viewModel.dataStateForTVSeries.observe(viewLifecycleOwner, Observer { dataState ->
            when(dataState){
                is DataState.Success<List<PopularTVSeries.Result>> -> {
                    tvSeriesListAdapter.submitList(dataState.data)
                    binding.progressBar.visibility = View.GONE
                }
                is DataState.Error -> {
                    binding.txtError.text =  dataState.Exception.message.toString()
                    binding.progressBar.visibility = View.GONE
                }
                is DataState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemSelected(position: Int, item: PopularTVSeries.Result) {
        println("Debug  $position")
        println("Debug  $item")
    }
}