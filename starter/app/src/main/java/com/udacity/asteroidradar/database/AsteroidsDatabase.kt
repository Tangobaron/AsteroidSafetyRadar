package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class AsteroidsDatabase: RoomDatabase() {
    abstract val asteroidDao: AsteroidDao

    companion object {
        @Volatile
        private var INSTANCE: AsteroidsDatabase? = null

        fun getInstance(context: Context): AsteroidsDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                                                    AsteroidsDatabase::class.java,
                                                    "asteroids_database")
                              .fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}