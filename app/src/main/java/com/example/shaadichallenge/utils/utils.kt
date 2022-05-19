package com.example.shaadichallenge.utils

import java.text.SimpleDateFormat
import java.util.*

fun String.intoDateFormat(): String {
    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val formatter = SimpleDateFormat("dd MMM", Locale.getDefault())
    return formatter.format(parser.parse(this)!!)
}
