package com.saiful.moviestvseries.view.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.saiful.moviestvseries.databinding.FragmentMoviesBinding
import com.saiful.moviestvseries.util.DataState
import com.saiful.moviestvseries.util.ItemDecorator
import com.saiful.moviestvseries.view.adapter.PopularMovieListAdapter
import com.saiful.moviestvseries.view.model.PopularMovies
import com.saiful.moviestvseries.view.viewModel.MainStateEvent
import com.saiful.moviestvseries.view.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MoviesFragment : Fragment(), PopularMovieListAdapter.Interaction
{
    private var _binding : FragmentMoviesBinding? = null
    private val binding get() = _binding!!

    private val viewModel : MainViewModel by viewModels()

    lateinit var movieListAdapter : PopularMovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return  binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setStateEvent(MainStateEvent.GetPopularMovies)
        subscribeObserver()
        initRecycler()
    }

    private fun initRecycler(){
        binding.recyclerview.apply {
            layoutManager = GridLayoutManager(activity, 3)
            val itemDecorator = ItemDecorator()
            addItemDecoration(itemDecorator)
            movieListAdapter = PopularMovieListAdapter(this@MoviesFragment)
            adapter = movieListAdapter
        }
    }

    private fun subscribeObserver() {
        viewModel.dataStateForMovies.observe(viewLifecycleOwner, Observer { dataState ->
            when(dataState){
                is DataState.Success<List<PopularMovies.Result>> -> {
                    movieListAdapter.submitList(dataState.data)
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

    override fun onItemSelected(position: Int, item: PopularMovies.Result) {
        println("Debug  $position")
        println("Debug  $item")
    }
}