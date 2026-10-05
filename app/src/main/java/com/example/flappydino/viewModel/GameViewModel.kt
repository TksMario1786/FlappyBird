package com.example.flappydino.viewModel
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
    private val gravity = 0.8f
    private val jumpForce = -13f
    private val pipeSpacing = 280f
    private val birdX = 80f
    private val birdHitboxSize = 35f
    private val pipeWidth = 80f
    private val gapSize = 150f
    private val _uiState = MutableStateFlow(GameUiState( pipes = createInitialPipes() ))
    private val pipeSpeed = 6f

    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()
    fun startGame(){
        _uiState.value = GameUiState(
            gameState = GameState.GAME,
            birdY = 150f,
            velocityY = 0f,
            pipes = createInitialPipes(),
            gameStarted = false,
            score = 0
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
                if (_uiState.value.gameStarted){
                    updateBird()
                    updatePipes()
                    if (checkCollision()){
                        _uiState.value = _uiState.value.copy(
                            gameState = GameState.GAME_OVER,
                            gameStarted = false
                        )
                    }
                }
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
            ),
            Pipe(
                x = 500f + (pipeSpacing * 3),
                gapY = randomGap()
            )
        )
    }
    fun updatePipes(){
        if (!uiState.value.gameStarted) return
        val currentPipes = _uiState.value.pipes
        val maxX = currentPipes.maxOfOrNull {it.x} ?: 0f
        var pointsToAdd = 0
        val updatedPipes =
            _uiState.value.pipes.map { pipe ->
                if (pipe.x < -100f) {
                    pointsToAdd++
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
            pipes = updatedPipes,
            score = _uiState.value.score + pointsToAdd
        )
    }
    private fun checkCollision(): Boolean {
        val current = _uiState.value
        val birdY = current.birdY

        if (birdY >= 350f){
            return true
        }
        val paddingY = (100f - birdHitboxSize) / 2f
        val birdLeft = birdX + 20f
        val birdRight = birdLeft + birdHitboxSize
        val birdTop = birdY + paddingY
        val birdBottom = birdTop + 30

        for (pipe in current.pipes) {
            val pipeLeft = pipe.x
            val pipeRight = pipeLeft + pipeWidth
            val isOverlapX = birdRight > pipeLeft && birdLeft < pipeRight

            if (isOverlapX){
                val gapTop = pipe.gapY-(gapSize/2)
                val gapBottom = (pipe.gapY + (gapSize / 2)) + 30f

                if (birdTop < gapTop || birdBottom > gapBottom){
                    return true
                }
            }
        }
        return false

    }
    fun onPlayerNameChange(newName: String) {
        _uiState.value = _uiState.value.copy(playerName = newName)
    }

    fun saveScoreAndRestart() {
        val currentScore = _uiState.value.score
        val inputName = _uiState.value.playerName
        var currentScores = _uiState.value.highScores

        val isHighScore = currentScores.size < 3 || currentScore > (currentScores.lastOrNull()?.second ?: 0)

        if (isHighScore && currentScore > 0) {
            val finalName = if (inputName.isBlank()) "Anónimo" else inputName
            currentScores = (currentScores + Pair(finalName, currentScore))
                .sortedByDescending { it.second }
                .take(3)
        }

        _uiState.value = GameUiState(
            gameState = GameState.GAME,
            birdY = 150f,
            velocityY = 0f,
            pipes = createInitialPipes(),
            gameStarted = false,
            score = 0,
            playerName = "",
            highScores = currentScores
        )
    }
    private fun randomGap(): Float{
        return (130..220).random().toFloat()
    }

}