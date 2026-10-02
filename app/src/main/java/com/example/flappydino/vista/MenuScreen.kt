package com.example.flappydino.vista
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.foundation.background
import androidx.compose.ui.unit.sp

@Composable
fun MenuScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF87CEEB)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        Text(
            text = "FlappyDino",
            color = Color.Green,
            fontSize = 48.sp

        )
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors( containerColor = Color.Blue)
        ){
            Text(text = "JUGAR")
        }

    }
}

