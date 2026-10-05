package com.example.flappydino.vista

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flappydino.R

@Composable
fun GameOverScreen(
    score: Int,
    playerName: String,
    highScores: List<Pair<String, Int>>,
    onPlayerNameChange: (String) -> Unit,
    onSaveAndRestart: () -> Unit
) {
    val isHighScore = highScores.size < 3 || score > (highScores.lastOrNull()?.second ?: 0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Cyan)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(id = R.string.game_over_title), fontSize = 40.sp, color = Color.Red, fontWeight = FontWeight.Bold)

        Text(text = stringResource(id = R.string.final_score, score), fontSize = 24.sp, modifier = Modifier.padding(vertical = 16.dp))

        if (isHighScore && score > 0) {
            Text(text = stringResource(id = R.string.new_record), color = Color.Blue, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = playerName,
                onValueChange = onPlayerNameChange,
                label = { Text(stringResource(id = R.string.enter_name)) },
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onSaveAndRestart) {
                Text(stringResource(id = R.string.save_and_retry))
            }
        } else {
            Button(onClick = onSaveAndRestart) {
                Text(stringResource(id = R.string.retry))
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(text = stringResource(id = R.string.top_scores_title), fontWeight = FontWeight.Bold)

        LazyColumn(
            modifier = Modifier.fillMaxWidth().height(150.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(highScores) { index, entry ->
                Text(
                    text = "${index + 1}. ${entry.first} - ${entry.second} pts",
                    modifier = Modifier.padding(4.dp),
                    fontSize = 18.sp,
                    fontWeight = if (entry.first == playerName && playerName.isNotBlank()) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}