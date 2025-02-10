package com.websarva.wings.keibapredictsupporter.DataClass

import com.google.gson.annotations.SerializedName

data class ShutubaData(
    val horseData: List<HorseData>
)

data class HorseData(
    @SerializedName("horse_url") val horseUrl: String, //馬のURL
    @SerializedName("horse_name") val horseName: String, //馬の名前
    @SerializedName("results") val pastResults: List<PastResults>, //過去のレース結果(空の可能性アリ)
    @SerializedName("matching_times") val times: List<String>, //走破データ
    val matched: Boolean //過去に同じ条件を使っているかどうか
)

data class PastResults(
    @SerializedName("日付") val date: String, //日付
    @SerializedName("開催") val venue: String, //開催
    @SerializedName("天気") val weather: String, //天候
    @SerializedName("R") val raceNumber: String, //レース番号
    @SerializedName("レース名") val raceName: String, //レース名
    @SerializedName("頭数") val numberOfHorses: String, //出走頭数
    @SerializedName("枠番") val frameNumber: String, //枠番
    @SerializedName("馬番") val horseNumber: String, //馬番
    @SerializedName("オッズ") val odds: String, //オッズ
    @SerializedName("人気") val popularity: String, //人気
    @SerializedName("着順") val finishingOrder: String, //着順
    @SerializedName("騎手") val jockey: String, //騎手
    @SerializedName("斤量") val weight: String, //斤量
    @SerializedName("距離") val distance: String, //距離
    @SerializedName("馬場") val trackCondition: String, //馬場状態
    @SerializedName("タイム") val time: String, //タイム
    @SerializedName("着差") val margin: String, //着差
    @SerializedName("通過") val passingOrder: String, //通過
    @SerializedName("ペース") val pace: String, //ペース
    @SerializedName("上がり") val last3F: String, //上がり3F
    @SerializedName("馬体重") val horseWeight: String //馬体重
)