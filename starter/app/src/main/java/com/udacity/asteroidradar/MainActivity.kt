package com.udacity.asteroidradar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import com.udacity.asteroidradar.database.AsteroidsDatabase
import java.util.concurrent.TimeUnit

class MainActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController = this.findNavController(R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this,
                                                     navController)
        val constraints: Constraints = Constraints.Builder()
                  .setRequiredNetworkType(NetworkType.CONNECTED).setRequiresCharging(true).build()
        val asteroidDao = AsteroidsDatabase.getInstance(applicationContext).asteroidDao
        val workBuilder = PeriodicWorkRequest.Builder(,
            1,
            TimeUnit.DAYS).setConstraints(constraints)
    }
}
