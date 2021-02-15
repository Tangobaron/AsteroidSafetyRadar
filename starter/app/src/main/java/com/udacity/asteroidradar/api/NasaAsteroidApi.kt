package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants.BASE_URL
import com.udacity.asteroidradar.PictureOfDay
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
private val retrofit = Retrofit.Builder().addConverterFactory(ScalarsConverterFactory.create())
          .addConverterFactory(MoshiConverterFactory.create(moshi)).baseUrl(BASE_URL).build()

interface NasaAsteroidApi {
    @GET("neo/rest/v1/feed?")
    suspend fun getAsteroidList(
              @QueryMap
              map: HashMap<String, String>): String

    @GET("planetary/apod?")
    suspend fun getImageOfDay(
              @Query("api_key")
              api_key: String): PictureOfDay
}

object NasaRetrofit {
    val retrofitService: NasaAsteroidApi by lazy {
        retrofit.create(NasaAsteroidApi::class.java)
    }
}
