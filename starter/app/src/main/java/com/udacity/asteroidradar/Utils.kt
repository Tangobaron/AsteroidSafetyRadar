package com.udacity.asteroidradar

import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun formattedDates(date: Date): String {
        var dates: String
        SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT,
                         Locale.getDefault()).also {dates = it.format(date)}
        return dates
    }
}