package com.udacity.asteroidradar.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.Asteroid

@Dao
interface AsteroidDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(asteroids: List<Asteroid>)

    @Query("SELECT * from Asteroid ORDER BY closeApproachDate DESC")
    suspend fun getAllAsteroid(): List<Asteroid>?

    @Query("SELECT * from Asteroid WHERE closeApproachDate = :todayDate ORDER BY closeApproachDate DESC")
    suspend fun getAsteroidOfDay(todayDate: String): List<Asteroid>?

}