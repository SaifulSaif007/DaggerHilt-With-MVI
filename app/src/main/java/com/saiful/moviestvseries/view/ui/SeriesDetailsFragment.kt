package com.saiful.moviestvseries.view.ui

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.saiful.moviestvseries.R
import com.saiful.moviestvseries.databinding.FragmentSeriesDetailsBinding
import com.saiful.moviestvseries.services.network.model.SeriesDetailsNetworkEntity
import com.saiful.moviestvseries.util.DataState
import com.saiful.moviestvseries.util.ItemDecorator
import com.saiful.moviestvseries.view.adapter.MovieTrailerAdapter
import com.saiful.moviestvseries.view.adapter.SeriesTrailerAdapter
import com.saiful.moviestvseries.view.model.SeriesDetails
import com.saiful.moviestvseries.view.viewModel.MainStateEvent
import com.saiful.moviestvseries.view.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.movie_list_item.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SeriesDetailsFragment : Fragment(), SeriesTrailerAdapter.Interaction {

    private var _binding : FragmentSeriesDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel : MainViewModel by viewModels()

    lateinit var trailerAdapter: SeriesTrailerAdapter

    companion object {
        var SeriesId : Int = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSeriesDetailsBinding.inflate(inflater, container, false )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(activity != null) {
            requireActivity().bottomNavigationView.visibility = View.GONE
            (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

            requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
        }

        Log.e("id", arguments?.getInt("series_id").toString())
        SeriesId = arguments?.getInt("series_id")!!

        viewModel.setStateEvent(MainStateEvent.GetSeriesDetails)
        initRecycler()
        subscribeObserver()

    }

    private fun initRecycler(){
        val layout =  LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.trailerRecycler.apply {
            layoutManager = layout
            val itemDecorator = ItemDecorator()
            setHasFixedSize(true)
            addItemDecoration(itemDecorator)

            trailerAdapter = SeriesTrailerAdapter(this@SeriesDetailsFragment)
            adapter = trailerAdapter
        }
    }

    @SuppressLint("SetTextI18n", "ResourceType")
    private fun subscribeObserver() {
        viewModel.dataStateSeriesDetails.observe(viewLifecycleOwner, Observer { dataState->
            when(dataState){
                is DataState.Success<SeriesDetails> -> {
                    binding.SeriesName.text = dataState.data.name
                    binding.seriesStatus.text = SeriesSts(dataState.data.status)
                    binding.seriesGenres.text =  SeriesGenres(dataState.data.genres)
                    binding.seriesRating.text = "Rating : " + dataState.data.voteAverage.toString() + " ( " + dataState.data.voteCount.toString() + " )"
                    binding.seriesFirstAirDate.text = "First Air Date : " +  dataState.data.firstAirDate
                    binding.seriesNoOfSeason.text = "number Of Seasons : " + dataState.data.numberOfSeasons.toString()
                    binding.seriesOverview.text = dataState.data.overview

                    Glide.with(activity?.applicationContext!!)
                        .load("http://image.tmdb.org/t/p/w500" + dataState.data.posterPath)
                        .transition(DrawableTransitionOptions.withCrossFade(600))
                        .error(R.drawable.nature)
                        .into(binding.posterImage.poster_image)

                    dataState.data.videos?.results?.let { trailerAdapter.submitList(it) }

                }
                is DataState.Error -> {
                    Log.e("error", dataState.Exception.toString() )
                }

            }
        })
    }

    override fun onDetach() {
        super.onDetach()
        requireActivity().bottomNavigationView.visibility = View.VISIBLE
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()

        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

    }

    private fun SeriesGenres(list: List<SeriesDetailsNetworkEntity.Genre?>? ) : String {
        var genres : String = ""
        if (list != null) {
            for (element in list){
                genres = genres +  "\u25AA " + element?.name + "   "
            }
        }
        return genres
    }

    public fun SeriesSts(status : String? ) : String {
        var sts : String = ""
        when(status.equals("Returning Series")) {
           true-> sts = "Running"
            false -> sts = status.toString()
        }

        return sts
    }

    override fun onItemSelected(position: Int, item: SeriesDetailsNetworkEntity.VideoResult) {

        try {
            val youtubeIntent  = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + item.key))
            youtubeIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(youtubeIntent)
        }
        catch (e : ActivityNotFoundException){
            val otherIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://youtube.com/watch?v=" + item.key))
            startActivity(otherIntent)
        }
    }
}