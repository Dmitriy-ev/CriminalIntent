package com.example.criminalintent.helpers

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.formatDate(): String {
    val locale = Locale("en", "EN")
    val dateFormat = SimpleDateFormat("EEEE yyyy-MM-dd", locale)
    return dateFormat.format(this)
}