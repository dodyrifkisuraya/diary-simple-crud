package com.d3if4202.diary

import android.annotation.SuppressLint
import java.text.SimpleDateFormat


@SuppressLint("SimpleDateFormat")
fun convertLongToDateString(systemTime: Long): String {
    return SimpleDateFormat("EEEE, MMM dd yyyy")
        .format(systemTime).toString()
}
