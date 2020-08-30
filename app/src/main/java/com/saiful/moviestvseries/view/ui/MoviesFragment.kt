package com.saiful.moviestvseries.view.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.saiful.moviestvseries.R
import com.saiful.moviestvseries.databinding.FragmentMoviesBinding
import com.saiful.moviestvseries.util.DataState
import com.saiful.moviestvseries.util.ItemDecorator
import com.saiful.moviestvseries.view.adapter.MoviePaginationListner
import com.saiful.moviestvseries.view.adapter.PopularMovieListAdapter
import com.saiful.moviestvseries.view.adapter.TvSeriesPaginationListner
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

    private lateinit var  fm : FragmentManager

    private val isLastPage = false
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        MoviePaginationListner.PAGE_START = 1
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return  binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fm = activity?.supportFragmentManager!!

        viewModel.setStateEvent(MainStateEvent.GetPopularMovies)
        subscribeObserver()
        initRecycler()
    }

    private fun initRecycler(){
        val layout  = GridLayoutManager(activity, 3)

        binding.recyclerview.apply {
            layoutManager = layout
            val itemDecorator = ItemDecorator()
            setHasFixedSize(true)
            addItemDecoration(itemDecorator)
            movieListAdapter = PopularMovieListAdapter(this@MoviesFragment)
            adapter = movieListAdapter
        }

        binding.recyclerview.addOnScrollListener(object: MoviePaginationListner(layout){
            override fun loadMoreItems() {
                isLoading = true
                PAGE_START++
                viewModel.setStateEvent(MainStateEvent.GetPopularMovies)

            }

            override fun isLastPage(): Boolean {
                return isLastPage;
            }

            override fun isLoading(): Boolean {
                return isLoading;
            }

        })
    }

    private fun subscribeObserver() {
        viewModel.dataStateForMovies.observe(viewLifecycleOwner, Observer { dataState ->
            when(dataState){
                is DataState.Success<List<PopularMovies.Result>> -> {

                    movieListAdapter.submitList(dataState.data)
                    isLoading = false
                    movieListAdapter.notifyDataSetChanged()
                    binding.progressBar.visibility = View.GONE
                }
                is DataState.Error -> {
                    binding.txtError.text =  dataState.Exception.message.toString()
                    binding.txtError.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                }
                is DataState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }

        })
    }

    override fun onDestroyView() {
        Log.e("moviefragment", "destroyed")
        super.onDestroyView()
        _binding = null
    }

    override fun onItemSelected(position: Int, item: PopularMovies.Result) {

        val detailFrag = MovieDetailsFragment()
        detailFrag.arguments = bundleOf("movie_id" to  item.id)

        fm.beginTransaction().add(R.id.fragmentContainerView, detailFrag, "movie_detail").show(detailFrag).hide(MainActivity.active)
            .addToBackStack("movies")
            .commit()
    }
}