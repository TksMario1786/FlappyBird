package com.example.flappydino

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.flappydino.ui.theme.FlappyDinoTheme
import com.example.flappydino.vista.MenuScreen
import com.example.flappydino.viewModel.GameViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flappydino.model.GameState
import com.example.flappydino.vista.GameScreen
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val gameViewModel: GameViewModel = viewModel()
            val uiState =
                gameViewModel.uiState
                    .collectAsStateWithLifecycle()

            FlappyDinoTheme {
                when (uiState.value.gameState) {
                    GameState.MENU -> {
                        MenuScreen(
                            onStartGame ={
                                gameViewModel.startGame(
                                )
                            }

                        )
                    }
                    GameState.GAME -> {
                        GameScreen(
                            birdY = uiState.value.birdY,
                            wingsDown = uiState.value.wingsDown,
                            pipes = uiState.value.pipes,
                            onJump = {
                                gameViewModel.jump()
                            }
                        )
                    }
                }
            }
        }
    }
}