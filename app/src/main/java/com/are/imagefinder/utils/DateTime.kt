package com.are.imagefinder.utils

import java.text.SimpleDateFormat
import java.util.*

class DateTime {

    fun formatDate(date: String?): String {
        return if (date.isNullOrBlank()) "" else {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz", Locale.getDefault())
            val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
            val finalDate = inputFormat.parse(date)
            outputFormat.format(finalDate!!)
        }
    }
}