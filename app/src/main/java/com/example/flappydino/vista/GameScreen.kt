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
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.res.painterResource
import com.example.flappydino.R
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import com.example.flappydino.model.Pipe
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun GameScreen (birdY: Float, onJump: () -> Unit, wingsDown: Boolean, pipes: List<Pipe>){
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val pipeWidth = 80.dp
    val pipeHeight = 300.dp
    val gapSize = 120.dp
    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF87CEEB))
            .clickable {onJump()}
    ){
        Image(
            painter = painterResource( R.drawable.fondo),
            contentDescription = "Fondo",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Text (
            text ="Puntuación: 0",
            color = Color.White,
            fontSize = 30.sp,
            modifier = Modifier.offset(
                x = 350.dp,
                y = 20.dp
            )



        )
        Image(
            painter = painterResource(
                id = if (wingsDown)
                    R.drawable.ptera2
                else R.drawable.ptera1
            ),
            contentDescription = "Pterosaurio",
            modifier = Modifier
                .size(100.dp)
                .offset(
                    x = 80.dp,
                    y = birdY.dp
                )
        )
        pipes.forEach{ pipe ->
            val gapSize = 120.dp
            val pipeHeight = 300.dp
            Image(
                painter = painterResource(
                    R.drawable.pipe_arriba
                ),
                contentDescription = "Obstáculo superior",
                modifier = Modifier
                    .offset(
                        x = pipe.x.dp,
                        y = (pipe.gapY - (gapSize.value / 2) - pipeHeight.value).dp

                    )
                    .size(
                        width = pipeWidth,
                        height = pipeHeight
                    )
            )
            Image(
                painter = painterResource(
                    R.drawable.pipe_abajo
                ),
                contentDescription = "Obstáculo inferior",
                modifier = Modifier
                    .offset(
                        x = pipe.x.dp,
                        y = (pipe.gapY + (gapSize.value / 2)).dp

                    )
                    .size(
                        width = pipeWidth,
                        height = pipeHeight
                    )
            )
            Text(
                text = pipe.x.toString(),
                color = Color.Red,
                modifier = Modifier.offset(
                    x = pipe.x.dp,
                    y = 100.dp
                )
            )
        }

    }


}