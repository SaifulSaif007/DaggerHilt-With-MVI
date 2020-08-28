package com.saiful.moviestvseries.view.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.saiful.moviestvseries.databinding.FragmentMovieDetailsBinding
import com.saiful.moviestvseries.util.DataState
import com.saiful.moviestvseries.view.model.MovieDetails
import com.saiful.moviestvseries.view.viewModel.MainStateEvent
import com.saiful.moviestvseries.view.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.movie_list_item.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private var _binding : FragmentMovieDetailsBinding? = null
    private val binding  get() = _binding!!

    private val viewModel : MainViewModel by viewModels()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(activity != null) {
            requireActivity().bottomNavigationView.visibility = View.GONE
            requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        }

        Log.d("id", arguments?.getInt("movie_id").toString())
        MovieId = arguments?.getInt("movie_id")!!

        viewModel.setStateEvent(MainStateEvent.GetMovieDetails)
        subscribeObserver()
    }


    @SuppressLint("SetTextI18n")
    private fun subscribeObserver() {
        viewModel.dataStateMovieDetails.observe(viewLifecycleOwner, Observer { dataState ->
            when(dataState){
                is DataState.Success<MovieDetails> -> {
                    binding.movieTitle.text = dataState.data.title
                    binding.movieTagline.text = dataState.data.tagline
                    binding.genres.text = dataState.data.genres?.get(1).toString()
                    binding.rating.text = dataState.data.voteAverage.toString() +  "(" +dataState.data.voteCount.toString() + ")"
                    binding.Duration.text = dataState.data.status
                    binding.ReleaseDate.text = dataState.data.releaseDate
                    binding.overview.text = dataState.data.overview

                    Glide.with(activity?.applicationContext!!)
                        .load("http://image.tmdb.org/t/p/w780" + dataState.data.posterPath)
                        .into(binding.posterImage.poster_image)
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
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }
}