package com.websarva.wings.keibapredictsupporter.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.websarva.wings.keibapredictsupporter.DataClass.HorseData
import com.websarva.wings.keibapredictsupporter.functions.convertToSeconds
import com.websarva.wings.keibapredictsupporter.functions.getMinTimeList

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HorseContent(
    horse: HorseData
) {
    Column {
        Row {
            Text(text = horse.horseName)
            Spacer(modifier = Modifier.width(8.dp))
            if(!horse.matched) Text(text = "※初条件")
        }
        FlowRow(maxItemsInEachRow = 12) {
            horse.times.forEach {
                val minTime = getMinTimeList(horse.times)
                Text(text = it[0], color = if(minTime == convertToSeconds(it[0])) Color.Red else Color.Black)
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}