package com.example.flappydino.model

data class GameUiState(
    val gameState: GameState = GameState.MENU,
    val birdY:  Float = 300f,
    val velocityY: Float = 0f,
    val score: Int = 0
)