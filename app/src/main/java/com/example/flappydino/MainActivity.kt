package com.example.flappydino

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.flappydino.ui.theme.FlappyDinoTheme
import com.example.flappydino.vista.MenuScreen
import com.example.flappydino.viewModel.GameViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flappydino.model.GameState
import com.example.flappydino.vista.GameScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.flappydino.vista.GameOverScreen

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
                            score = uiState.value.score,
                            onJump = {
                                gameViewModel.jump()
                            }
                        )
                    }
                    GameState.GAME_OVER -> {
                        GameOverScreen(
                            score = uiState.value.score,
                            playerName = uiState.value.playerName,
                            highScores = uiState.value.highScores,
                            onPlayerNameChange = { newName ->
                                gameViewModel.onPlayerNameChange(newName)
                            },
                            onSaveAndRestart = {
                                gameViewModel.saveScoreAndRestart()
                            }
                        )
                    }
                }
            }
        }
    }
}