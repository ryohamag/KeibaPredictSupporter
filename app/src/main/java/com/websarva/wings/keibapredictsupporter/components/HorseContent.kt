package com.websarva.wings.keibapredictsupporter.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.websarva.wings.keibapredictsupporter.DataClass.HorseData

@Composable
fun HorseContent(
    horse: HorseData
) {
    Column {
        Text(text = horse.horseName)
        Row {
            horse.times.forEach {
                Text(text = it[0])
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}