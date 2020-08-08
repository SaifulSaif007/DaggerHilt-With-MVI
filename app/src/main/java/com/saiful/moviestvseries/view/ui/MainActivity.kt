package com.saiful.moviestvseries.view.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.saiful.moviestvseries.R
import com.saiful.moviestvseries.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        val navController = findNavController(R.id.fragmentContainerView)

        mainBinding.bottomNavigationView.setupWithNavController(navController)


        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.moviesFragment,
            R.id.TVSeriesFragment
        ))
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

}