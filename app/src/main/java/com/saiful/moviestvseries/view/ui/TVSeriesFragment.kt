package com.saiful.moviestvseries.view.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.RequestManager
import com.saiful.moviestvseries.R
import com.saiful.moviestvseries.databinding.FragmentTvSeriesBinding
import com.saiful.moviestvseries.util.DataState
import com.saiful.moviestvseries.util.ItemDecorator
import com.saiful.moviestvseries.view.adapter.MoviePaginationListner
import com.saiful.moviestvseries.view.adapter.PopularMovieListAdapter
import com.saiful.moviestvseries.view.adapter.PopularTvSeriesListAdapter
import com.saiful.moviestvseries.view.adapter.TvSeriesPaginationListner
import com.saiful.moviestvseries.view.adapter.TvSeriesPaginationListner.Companion.PAGE_START
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

    private lateinit var  fm : FragmentManager

    private  val isLastPage = false
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        PAGE_START = 1
        _binding = FragmentTvSeriesBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fm = activity?.supportFragmentManager!!

        subscribeObserver()
        viewModel.setStateEvent(MainStateEvent.GetPopularTVSeries)
        initRecycler()
    }

    private fun initRecycler(){
        val layout  = GridLayoutManager(activity, 3)

        binding.recyclerview.apply {
            layoutManager = layout
            val itemDecorator = ItemDecorator()
            setHasFixedSize(true)
            addItemDecoration(itemDecorator)
            tvSeriesListAdapter = PopularTvSeriesListAdapter(this@TVSeriesFragment)
            adapter = tvSeriesListAdapter
        }

        binding.recyclerview.addOnScrollListener(object: TvSeriesPaginationListner(layout){
            override fun loadMoreItems() {
                isLoading = true
                PAGE_START++
                viewModel.setStateEvent(MainStateEvent.GetPopularTVSeries)
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
        viewModel.dataStateForTVSeries.observe(viewLifecycleOwner, Observer { dataState ->
            when(dataState){
                is DataState.Success<List<PopularTVSeries.Result>> -> {
                    tvSeriesListAdapter.submitList(dataState.data)
                    isLoading = false
                    tvSeriesListAdapter.notifyDataSetChanged()
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
        super.onDestroyView()
        _binding = null
    }


    @SuppressLint("RestrictedApi")
    override fun onItemSelected(position: Int, item: PopularTVSeries.Result) {

        val detailFrag = SeriesDetailsFragment()
        detailFrag.arguments = bundleOf("series_id" to item.id)


        fm.beginTransaction().add(R.id.fragmentContainerView, detailFrag, "series_detail").show(detailFrag).hide(MainActivity.active)
            .addToBackStack("series")
            .setCustomAnimations(R.anim.fragment_open_enter,R.anim.fragment_close_exit, R.anim.nav_default_pop_enter_anim, R.anim.nav_default_pop_exit_anim)
            .commit()

    }
}