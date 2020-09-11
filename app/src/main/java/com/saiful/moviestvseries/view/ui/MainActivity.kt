package com.saiful.moviestvseries.view.ui

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.fxn.Bubble
import com.fxn.BubbleTabBar
import com.fxn.OnBubbleClickListener
import com.github.florent37.shapeofview.shapes.BubbleView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.saiful.moviestvseries.R
import com.saiful.moviestvseries.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    private val movieFragment : MoviesFragment = MoviesFragment()
    private val seriesFragment : TVSeriesFragment = TVSeriesFragment()

    private val fm : FragmentManager = supportFragmentManager

    private lateinit var navigation : BubbleTabBar

    companion object {
         lateinit var active : Fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        navigation = mainBinding.bottomNavigationView

        active = movieFragment
        supportActionBar?.title = "Popular Movies"

        fm.beginTransaction().replace(R.id.fragmentContainerView, movieFragment, "movies").commit()
        fm.beginTransaction().add(R.id.fragmentContainerView, seriesFragment, "series").hide(seriesFragment).commit()



        navigation.addBubbleListener(object : OnBubbleClickListener {
            override fun onBubbleClick(id: Int) {
                if(id == R.id.moviesFragment){
                    fm.beginTransaction()
                        .setCustomAnimations(R.anim.fragment_open_enter,R.anim.fragment_close_exit, R.anim.nav_default_pop_enter_anim, R.anim.nav_default_pop_exit_anim)
                        .hide(active).show(movieFragment).commit()

                    active = movieFragment
                    supportActionBar?.title = "Popular Movies"
                }
                else {
                    fm.beginTransaction()
                        .setCustomAnimations(R.anim.fragment_open_enter,R.anim.fragment_close_exit, R.anim.nav_default_pop_enter_anim, R.anim.nav_default_pop_exit_anim)
                        .hide(active).show(seriesFragment).commit()

                    active = seriesFragment
                    supportActionBar?.title = "Popular Series"

                }
            }
        })
    }
}