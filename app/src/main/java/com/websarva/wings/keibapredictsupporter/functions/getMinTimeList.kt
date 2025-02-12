package com.websarva.wings.keibapredictsupporter.functions

import android.util.Log

//持ち時計の最小値を取得する関数
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