package com.saiful.moviestvseries.view.ui

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.saiful.moviestvseries.R
import com.saiful.moviestvseries.databinding.FragmentMovieDetailsBinding
import com.saiful.moviestvseries.services.network.model.MovieDetailsNetworkEntity
import com.saiful.moviestvseries.util.DataState
import com.saiful.moviestvseries.util.ItemDecorator
import com.saiful.moviestvseries.view.adapter.MovieTrailerAdapter
import com.saiful.moviestvseries.view.model.MovieDetails
import com.saiful.moviestvseries.view.viewModel.MainStateEvent
import com.saiful.moviestvseries.view.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.movie_list_item.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MovieDetailsFragment : Fragment(), MovieTrailerAdapter.Interaction {

    private var _binding : FragmentMovieDetailsBinding? = null
    private val binding  get() = _binding!!

    private val viewModel : MainViewModel by viewModels()

    lateinit var trailerAdapter : MovieTrailerAdapter

    companion object {
       var MovieId : Int = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(activity != null) {
            requireActivity().bottomNavigationView.visibility = View.GONE

            requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
        }

        Log.d("id", arguments?.getInt("movie_id").toString())
        MovieId = arguments?.getInt("movie_id")!!

        viewModel.setStateEvent(MainStateEvent.GetMovieDetails)
        initRecycler()
        subscribeObserver()

    }


    private fun initRecycler(){
        val layout =  LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        binding.TrailerRecyclerView.apply {
            layoutManager = layout
            val itemDecorator = ItemDecorator()
            setHasFixedSize(true)
            addItemDecoration(itemDecorator)

            trailerAdapter = MovieTrailerAdapter(this@MovieDetailsFragment)
            adapter = trailerAdapter
        }


    }

    @SuppressLint("SetTextI18n")
    private fun subscribeObserver() {
        viewModel.dataStateMovieDetails.observe(viewLifecycleOwner, Observer { dataState ->
            when(dataState){
                is DataState.Success<MovieDetails> -> {
                    binding.movieTitle.text = dataState.data.title
                    binding.movieTagline.text = dataState.data.tagline
                    binding.genres.text =  MovieGenres(dataState.data.genres)
                    binding.rating.text = "Rating : "  + dataState.data.voteAverage.toString() +  "  ( " +dataState.data.voteCount.toString() + " )"
                    binding.Duration.text = "Status : " + dataState.data.status
                    binding.ReleaseDate.text = "Release Date : " + dataState.data.releaseDate
                    binding.overview.text = dataState.data.overview

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

    @SuppressLint("RestrictedApi")
    override fun onDetach() {
        super.onDetach()
        requireActivity().bottomNavigationView.visibility = View.VISIBLE

        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

    }

    private fun MovieGenres(list: List<MovieDetailsNetworkEntity.Genre?>?) : String{
        var genres : String = ""
        if (list != null) {
            for (element in list){
                genres = genres +  "\u25AA " + element?.name + "   "
            }
        }
        return genres
    }

    override fun onItemSelected(position: Int, item: MovieDetailsNetworkEntity.VideoResult) {
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