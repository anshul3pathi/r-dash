package com.example.rdash.utils

import java.text.SimpleDateFormat
import java.util.Locale

const val ISO_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ"
const val D_MMM_yy = "d MMM, yy"
const val hh_mm_a = "hh:mm a"

fun String.toDateIn_d_MMM_yy(): String? {
    val inputFormatter = SimpleDateFormat(ISO_DATE_FORMAT, Locale.getDefault())
    val parsedDate = inputFormatter.parse(this)

    val outputFormatter = SimpleDateFormat(D_MMM_yy, Locale.getDefault())
    return parsedDate?.let { outputFormatter.format(it) }
}

fun String.toTimeIn_hh_mm_a(): String? {
    val inputFormatter = SimpleDateFormat(ISO_DATE_FORMAT, Locale.getDefault())
    val parsedDate = inputFormatter.parse(this)

    // Format the time in the desired output format
    val outputFormatter = SimpleDateFormat(hh_mm_a, Locale.getDefault())
    return parsedDate?.let { outputFormatter.format(it) }
}