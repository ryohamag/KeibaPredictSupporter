package com.websarva.wings.keibapredictsupporter.functions

import android.content.Context
import java.io.File

fun saveJsonToFile(context: Context, fileName: String, jsonData: String) {
    val file = File(context.filesDir, fileName)  // 内部ストレージのファイルパス
    file.writeText(jsonData)  // JSONデータを書き込む
}

fun readJsonFromFile(context: Context, fileName: String): String? {
    val file = File(context.filesDir, fileName)
    return if (file.exists()) file.readText() else null
}

fun deleteJsonFile(context: Context, fileName: String): Boolean {
    val file = File(context.filesDir, fileName)
    return file.delete()
}

