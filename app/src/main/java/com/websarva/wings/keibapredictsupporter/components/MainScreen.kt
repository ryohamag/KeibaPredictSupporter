package com.websarva.wings.keibapredictsupporter.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.websarva.wings.keibapredictsupporter.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    innerPadding: PaddingValues,
    viewModel: MainViewModel // ViewModel を Compose のライフサイクルで管理
) {
    val shutubaData by viewModel.shutubaData.observeAsState(emptyList()) // LiveData を監視
    val raceCourseList = mapOf(
        "01" to "札幌",
        "02" to "函館",
        "03" to "福島",
        "04" to "新潟",
        "05" to "東京",
        "06" to "中山",
        "07" to "中京",
        "08" to "京都",
        "09" to "阪神",
        "10" to "小倉",
    )

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .padding(8.dp)
    ) {
        Row {
            OutlinedTextField(
                value = viewModel.year.value,
                onValueChange = { newText ->
                    if (newText.length <= 4 && newText.all { it.isDigit() }) {
                        viewModel.year.value = newText
                    }
                },
                modifier = Modifier.weight(1f),
                label = { Text("年") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                maxLines = 1,
            )

            Spacer(modifier = Modifier.width(2.dp))

            var expanded by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.weight(1.5f),
            ) {
                OutlinedTextField(
                    value = raceCourseList[viewModel.racecourse.value] ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("競馬場") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    raceCourseList.forEach { (key, value) ->
                        DropdownMenuItem(
                            text = { Text(value) },
                            onClick = {
                                Log.d("MainScreen", "racecourse: ${viewModel.racecourse.value}")
                                viewModel.racecourse.value = key
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(2.dp))

            OutlinedTextField(
                value = viewModel.round.value,
                onValueChange = { newText ->
                    if (newText.length <= 2 && newText.all { it.isDigit() }) {
                        viewModel.round.value = newText
                    }
                },
                modifier = Modifier.weight(1f),
                label = { Text("回") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                maxLines = 1,
            )

            Spacer(modifier = Modifier.width(2.dp))

            OutlinedTextField(
                value = viewModel.day.value,
                onValueChange = { newText ->
                    if (newText.length <= 2 && newText.all { it.isDigit() }) {
                        viewModel.day.value = newText
                    }
                },
                modifier = Modifier.weight(1f),
                label = { Text("日") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                maxLines = 1,
            )

            Spacer(modifier = Modifier.width(2.dp))

            OutlinedTextField(
                value = viewModel.numOfRace.value,
                onValueChange = { newText ->
                    if (newText.length <= 2 && newText.all { it.isDigit() }) {
                        viewModel.numOfRace.value = newText
                    }
                },
                modifier = Modifier.weight(1f),
                label = { Text("レース") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                maxLines = 1,
            )
        }

        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Button(
                onClick = {
                    viewModel.fetchShutubaData()
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("リクエストを送信")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    viewModel.readJsonFromFile()
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("ファイルを読み込む")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (shutubaData.isNotEmpty()) {
            Row(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = shutubaData[0].raceTitle)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = shutubaData[0].raceType + "m")
            }
        }

        // 受け取ったデータをリストとして表示
        LazyColumn {
            items(shutubaData.size) { index ->
                val horse = shutubaData[index]
                ListItem(
                    headlineContent = { HorseContent(horse) },
                    leadingContent = {
                        Text(text = String.format("%2d", index + 1))
                    }
                )
                HorizontalDivider()
            }
        }
    }
}
