package com.example.flappydino.viewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.flappydino.model.GameState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.flappydino.model.GameUiState
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameViewModel : ViewModel(){
    private val gravity = 1f
    private val jumpForce = -20f
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()
    fun startGame(){
        _uiState.value =
            _uiState.value.copy(
                gameState = GameState.GAME
            )
    }
    fun jump(){
        _uiState.value =
            _uiState.value.copy(
                velocityY = jumpForce
            )
    }
    fun updateBird(){
        val current = _uiState.value
        val newVelocity = current.velocityY + gravity
        val newBirdY = (current.birdY + newVelocity).coerceIn(-430f,430f)
        _uiState.value = current.copy(
            birdY = newBirdY,
            velocityY = newVelocity
        )
    }
    init {
        viewModelScope.launch {
            while (true) {
                updateBird()
                delay(16)
            }
        }
    }



}