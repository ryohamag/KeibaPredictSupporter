package com.websarva.wings.keibapredictsupporter.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.websarva.wings.keibapredictsupporter.MainViewModel

@Composable
fun MainScreen(
    innerPadding: PaddingValues,
    viewModel: MainViewModel = MainViewModel(context = LocalContext.current) // ViewModel を Compose のライフサイクルで管理
) {
    val shutubaData by viewModel.shutubaData.observeAsState(emptyList()) // LiveData を監視

    Column(
        modifier = Modifier.padding(innerPadding)
    ) {
        Button(
            onClick = {
                viewModel.fetchShutubaData("202506010911.json")
            },
        ) {
            Text("リクエストを送信")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                viewModel.readJsonFromFile("202506010911.json")
            },
        ) {
            Text("ファイルを読み込む")
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
