package com.example.flappydino.vista

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.offset
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable


@Composable
fun GameScreen (birdY: Float, onJump: () -> Unit){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF87CEEB))
            .clickable {onJump()},
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text (
            text ="Puntuación: 0",
            color = Color.White,
            fontSize = 25.sp

        )
        Text(
            text = "Y: $birdY"
        )
        Text (
            text = "🦅",
            modifier = Modifier.offset(y = birdY.dp),
            fontSize = 50.sp
        )
    }
}