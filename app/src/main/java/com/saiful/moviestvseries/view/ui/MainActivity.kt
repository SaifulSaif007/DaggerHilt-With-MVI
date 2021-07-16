package com.saiful.moviestvseries.view.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.saiful.moviestvseries.R
import com.saiful.moviestvseries.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    private val movieFragment: MoviesFragment = MoviesFragment()
    private val seriesFragment: TVSeriesFragment = TVSeriesFragment()

    private val fm: FragmentManager = supportFragmentManager

    companion object {
        lateinit var active: Fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)


        active = movieFragment
        supportActionBar?.title = "Popular Movies"

        fm.beginTransaction().replace(R.id.fragmentContainerView, movieFragment, "movies").commit()
        fm.beginTransaction().add(R.id.fragmentContainerView, seriesFragment, "series")
            .hide(seriesFragment).commit()

        mainBinding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            if (item.itemId == R.id.moviesFragment) {
                fm.beginTransaction()
                    .setCustomAnimations(
                        R.anim.nav_default_enter_anim,
                        R.anim.nav_default_exit_anim,
                        R.anim.nav_default_pop_enter_anim,
                        R.anim.nav_default_pop_exit_anim
                    )
                    .hide(active).show(movieFragment).commit()

                active = movieFragment
                supportActionBar?.title = "Popular Movies"
            } else {
                fm.beginTransaction()
                    .setCustomAnimations(
                        R.anim.nav_default_enter_anim,
                        R.anim.nav_default_exit_anim,
                        R.anim.nav_default_pop_enter_anim,
                        R.anim.nav_default_pop_exit_anim
                    )
                    .hide(active).show(seriesFragment).commit()

                active = seriesFragment
                supportActionBar?.title = "Popular Series"

            }

            true
        }
    }

}


