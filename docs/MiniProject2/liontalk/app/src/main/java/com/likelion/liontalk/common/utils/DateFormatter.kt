package com.likelion.liontalk.common.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateFormatter {
    fun format(timestamp: Long) : String {
        val sdf = SimpleDateFormat("HH:mm" , Locale.getDefault())
        return sdf.format(Date(timestamp))
    }
}