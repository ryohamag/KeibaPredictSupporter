package com.websarva.wings.keibapredictsupporter.functions

import android.util.Log

fun getMinTimeList(times: List<List<String>>): Float {
    val timeList = mutableListOf<Float>()
    var time: Float
    times.forEach {
        Log.d("getMinTimeList", "it: $it")
        time = convertToSeconds(it[0])
        timeList.add(time)
    }
    return timeList.minOrNull()!!
}