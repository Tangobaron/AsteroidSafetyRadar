package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.Utils
import com.udacity.asteroidradar.api.NasaRetrofit
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDao
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.*
import kotlin.collections.HashMap

class MainViewModel(
          private val asteroidDao: AsteroidDao, application: Application): AndroidViewModel(application) {
    private val endDate = Utils.formattedDates(Calendar.getInstance().time)
    var pictureOfDay = MutableLiveData<PictureOfDay>()
    var asteroidsLiveDAta = MutableLiveData<List<Asteroid>>()

    init {
        addAllAsteroids()
        addPictureOfDay()
    }

    fun getAsteroidOfDay() {
        viewModelScope.launch {
            asteroidsLiveDAta.value = asteroidDao.getAsteroidOfDay(endDate)
        }
    }

    fun getAsteroidOfWeek() {
        viewModelScope.launch {
            asteroidsLiveDAta.value = asteroidDao.getAllAsteroid()
        }
    }

    fun addAsteroidToDatabase(asteroidDao: AsteroidDao) {
        viewModelScope.launch {
            fetchAsteroidList(asteroidDao)
            asteroidsLiveDAta.value = asteroidDao.getAsteroidOfDay(endDate)
        }
    }

    fun addAllAsteroids() {
        viewModelScope.launch {
            asteroidsLiveDAta.value = asteroidDao.getAllAsteroid()
        }
    }

    fun addPictureOfDay() {
        viewModelScope.launch {
            getPictureOfDay()
        }
    }

    private suspend fun getPictureOfDay() {
        try {
            pictureOfDay.value = NasaRetrofit.retrofitService.getImageOfDay(API_KEY)
        } catch (e: Exception) {
            pictureOfDay.value = PictureOfDay("",
                                              "",
                                              "")
            Log.d("Picture Of Day Error",
                  "${e.message}")
        }
    }

    fun saveToDatabase(asteroidDao: AsteroidDao) {
        viewModelScope.launch {
            fetchAsteroidList(asteroidDao)
            asteroidsLiveDAta.value = asteroidDao.getAsteroidOfDay(endDate)
        }
    }

    private suspend fun fetchAsteroidList(asteroidDao: AsteroidDao) {
        val startDate = Utils.formattedDates(Calendar.getInstance().also {
            it.add(Calendar.DAY_OF_YEAR,
                   -Constants.DEFAULT_END_DATE_DAYS)
        }.time)
        val hashMap = HashMap<String, String>()
        hashMap.put("start_date",
                    startDate)
        hashMap.put("end_date",
                    endDate)
        hashMap.put("api_key",
                    Constants.API_KEY)
        try {
            val parser = JSONObject(NasaRetrofit.retrofitService.getAsteroidList(hashMap))
            asteroidDao.insert(parseAsteroidsJsonResult(parser))
            Log.d("Parsing",
                  "Successful")
        } catch (e: Exception) {
            Log.d("Parsing",
                  "Failure to fetch ${e.message}")
        }

    }
}