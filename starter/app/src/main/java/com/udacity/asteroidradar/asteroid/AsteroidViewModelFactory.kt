package com.udacity.asteroidradar.asteroid

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroidradar.database.AsteroidDao
import com.udacity.asteroidradar.main.MainViewModel

class AsteroidViewModelFactory(
          private val asteroidDao: AsteroidDao, private val application: Application): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T: ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(asteroidDao,
                                 application) as T
        }
        throw  IllegalArgumentException("Unknown ViewModel class")
    }
}