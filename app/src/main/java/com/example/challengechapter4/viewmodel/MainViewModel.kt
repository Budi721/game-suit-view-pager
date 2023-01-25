package com.example.challengechapter4.viewmodel

import androidx.lifecycle.ViewModel
import com.example.challengechapter4.domain.AppState
import com.example.challengechapter4.domain.GameState
import kotlinx.coroutines.flow.*
import kotlin.random.Random

class MainViewModel : ViewModel() {
    companion object {
        val OPTION = arrayOf("batu", "kertas", "gunting")
    }

    private val _uiState = MutableStateFlow(AppState())
    val uiState: StateFlow<AppState> = _uiState.asStateFlow()

    fun startGame(chosen: String) {
        _uiState.update {
            if (it.state == GameState.FINISH){
                return
            }

            it.copy(
                chosen = mapOf(Pair("player", chosen), Pair("computer", OPTION[Random.nextInt(from = 0, until = 3)])),
                state = GameState.FINISH
            )
        }

        calculateWinner()
    }

    fun restartGame() {
        _uiState.update {
            it.copy(
                chosen = mapOf(Pair("player", ""), Pair("computer", "")),
                state = GameState.START,
                result = ""
            )
        }
    }

    private fun calculateWinner() {
        _uiState.getAndUpdate {
            val player = it.chosen["player"]
            val computer = it.chosen["computer"]
            val result = if (player == "gunting" && computer == "kertas") {
                "player"
            } else if (computer == "gunting" && player == "kertas") {
                "computer"
            } else if (player == "batu" && computer == "kertas") {
                "computer"
            } else if (computer == "batu" && player == "kertas") {
                "player"
            } else if (player == "batu" && computer == "gunting") {
                "player"
            } else if (computer == "batu" && player == "gunting") {
                "computer"
            } else {
                "draw"
            }

            it.copy(result = result)
        }

    }


}