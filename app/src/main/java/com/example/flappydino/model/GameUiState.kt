package com.example.flappydino.model

data class GameUiState(
    val gameState: GameState = GameState.MENU,
    val birdY:  Float = 150f,
    val velocityY: Float = 0f,
    val score: Int = 0,
    val wingsDown: Boolean = false,
    val gameStarted: Boolean =false,
    val pipes: List<Pipe> = emptyList()

)