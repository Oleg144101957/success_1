package tv.rasucc.ov.time.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import tv.rasucc.ov.time.Constants
import tv.rasucc.ov.time.domain.storage.PointsStorage
import tv.rasucc.ov.time.presentation.model.Action
import tv.rasucc.ov.time.presentation.model.GameState
import javax.inject.Inject


@HiltViewModel
class GameViewModel @Inject constructor(private val pointsStorage: PointsStorage) :
    ViewModel() {

    private val lock = Any()

    private val _power: MutableStateFlow<Long> = MutableStateFlow(Constants.defaultPower)
    val power: StateFlow<Long> = _power

    private val _health: MutableStateFlow<Int> = MutableStateFlow(30)
    val health: StateFlow<Int> = _health

    private val _points: MutableStateFlow<Int> = MutableStateFlow(0)
    val points: StateFlow<Int> = _points

    private val _gameState: MutableStateFlow<GameState> = MutableStateFlow(GameState.STARTING)
    val gameState: StateFlow<GameState> = _gameState

    private var isIncreasing = false


    fun submitAction(action: Action) {
        when (action) {
            is Action.IncreasePoints -> {
                increasePoints()
            }

            is Action.DecreaseHealth -> {
                decreaseHealth()
            }

            is Action.StartPlaying -> {
                setStateIsPlaying()
            }

            is Action.FinishGame -> {
                setStateIsGameOver()
            }

            is Action.StartIncreasingPower -> {
                increasePower()
            }

            is Action.FinishIncreasingPower -> {
                setDefaultPower()
            }
        }
    }

    private fun setDefaultPower() {
        isIncreasing = false
        _power.value = Constants.defaultPower
    }

    private fun increasePower() {

        isIncreasing = true

        viewModelScope.launch {
            for (i in 0..4) {
                delay(500)
                if (power.value > 799) {
                    synchronized(lock) {
                        if (isIncreasing) {
                            _power.value = _power.value - 200L
                        }
                    }
                }
            }
        }
    }

    private fun setStateIsGameOver() {
        viewModelScope.launch {
            _gameState.emit(GameState.GAME_OVER)
        }
    }

    private fun setStateIsPlaying() {
        viewModelScope.launch {
            _gameState.emit(GameState.PLAYING)
        }
    }

    private fun decreaseHealth() {
        val currentHealth = _health.value
        viewModelScope.launch {
            _health.emit(currentHealth - 1)
        }
    }

    private fun increasePoints() {
        val currentPoints = _points.value
        viewModelScope.launch {
            pointsStorage.savePoints(currentPoints + 1)
            _points.emit(currentPoints + 1)
        }
    }
}