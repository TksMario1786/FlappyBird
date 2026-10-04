package com.example.flappydino.viewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.flappydino.model.GameState

class GameViewModel : ViewModel(){
    var gameState by mutableStateOf(GameState.MENU)
        private set
    fun startGame(){
        gameState = GameState.GAME
    }

}