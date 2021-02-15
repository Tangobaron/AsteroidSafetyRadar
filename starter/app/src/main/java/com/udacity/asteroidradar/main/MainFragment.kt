package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.AsteroidAdapter
import com.udacity.asteroidradar.AsteroidListener
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.asteroid.AsteroidViewModelFactory
import com.udacity.asteroidradar.database.AsteroidsDatabase
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment: Fragment() {
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
              inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        val application = requireNotNull(activity).application
        val asteroidDao = AsteroidsDatabase.getInstance(application).asteroidDao
        val asteroidViewModelFactory = AsteroidViewModelFactory(asteroidDao,
                                                                application)
        viewModel = ViewModelProvider(this,
                                      asteroidViewModelFactory).get(MainViewModel::class.java)
        val adapter = AsteroidAdapter(AsteroidListener {asteroidId ->
            val asteroid = viewModel.asteroidsLiveDAta.value?.find {
                it.id == asteroidId
            }
            this.findNavController().navigate(MainFragmentDirections.actionShowDetail(asteroid!!))
        })

        binding.asteroidRecycler.adapter = adapter
        viewModel.asteroidsLiveDAta.observe(viewLifecycleOwner,
                                            {
                                                it?.let {
                                                    if (it.isNullOrEmpty()) {
                                                        viewModel.addAsteroidToDatabase(asteroidDao)
                                                    } else {
                                                        adapter.submitList(it)
                                                        binding.statusLoadingWheel.visibility = View.GONE
                                                    }
                                                }
                                            })

        binding.lifecycleOwner = this
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu,
                         menu)
        super.onCreateOptionsMenu(menu,
                                  inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId)->{
            R.id.show_week_menu->{viewModel}
        }
    }
}
