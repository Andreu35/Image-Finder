package com.are.imagefinder.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class DateTime {

    fun formatDate(date: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssz", Locale.getDefault())
        return LocalDate.parse(date, formatter).toString()
    }
}