package com.websarva.wings.keibapredictsupporter.functions

//秒数に変換する関数
fun convertToSeconds(time: String): Float? {
    if(time == "初出走") return null
    val minute = time.split(":")
    val second = minute[1].split(".")
    return minute[0].toFloat() * 60 + second[0].toFloat() + (second[1].toFloat() / 10)
}