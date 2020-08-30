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

    private lateinit var navigation : BottomNavigationView

    companion object {
         lateinit var active : Fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        navigation = mainBinding.bottomNavigationView
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        active = movieFragment

        fm.beginTransaction().replace(R.id.fragmentContainerView, movieFragment, "movies").commit()
        fm.beginTransaction().add(R.id.fragmentContainerView, seriesFragment, "series").hide(seriesFragment).commit()

    }

    private val mOnNavigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener {

            if(it.itemId == R.id.moviesFragment) {
                fm.beginTransaction().hide(active).show(movieFragment).commit()
                active = movieFragment
                return@OnNavigationItemSelectedListener true
            }
            else if(it.itemId == R.id.TVSeriesFragment){
                fm.beginTransaction().hide(active).show(seriesFragment).commit()
                active = seriesFragment
                return@OnNavigationItemSelectedListener true
            }

            return@OnNavigationItemSelectedListener false

        }

}