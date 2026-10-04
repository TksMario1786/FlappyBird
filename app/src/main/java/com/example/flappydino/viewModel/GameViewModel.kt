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
import com.example.flappydino.model.Pipe

class GameViewModel : ViewModel(){
    private val gravity = 1f
    private val jumpForce = -20f
    private val _uiState = MutableStateFlow(GameUiState( pipes = createInitialPipes() ))
    private val pipeSpeed = 6f
    private val pipeSpacing = 280f
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
                velocityY = jumpForce,
                wingsDown = true,
                gameStarted = true
            )
        viewModelScope.launch{
            delay(150)
            _uiState.value = _uiState.value.copy( wingsDown = false)
        }
    }
    fun updateBird(){
        if (!uiState.value.gameStarted) return
        val current = _uiState.value
        val newVelocity = current.velocityY + gravity
        val newBirdY = (current.birdY + newVelocity).coerceIn(-55f,350f)
        _uiState.value = current.copy(
            birdY = newBirdY,
            velocityY = newVelocity
        )
    }
    init {
        viewModelScope.launch {
            while (true) {
                updateBird()
                updatePipes()
                delay(16)
            }
        }
    }
    private fun createInitialPipes(): List<Pipe>{
        return listOf(
            Pipe(
                x = 500f,
                gapY = randomGap()
            ),
            Pipe(
                x = 500f + pipeSpacing,
                gapY = randomGap()
            ) ,
            Pipe(
                x = 500f + (pipeSpacing * 2),
                gapY = randomGap()
            )
        )
    }
    fun updatePipes(){
        if (!uiState.value.gameStarted) return
        val currentPipes = _uiState.value.pipes
        val maxX = currentPipes.maxOfOrNull {it.x} ?: 0f
        val updatedPipes =
            _uiState.value.pipes.map { pipe ->
                if (pipe.x < -100f) {
                    Pipe(
                        x = maxX + pipeSpacing,
                        gapY = randomGap()
                    )
                } else {
                    pipe.copy(
                        x = pipe.x - pipeSpeed
                    )
                }
            }
        _uiState.value = _uiState.value.copy(
            pipes = updatedPipes
        )
    }
    private fun randomGap(): Float{
        return (100..280).random().toFloat()
    }

}