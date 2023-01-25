package com.example.challengechapter4.domain

data class AppState(
    val chosen: Map<String, String> = mapOf(Pair("player", ""), Pair("computer", "")),
    val state: GameState = GameState.START,
    val result: String = ""
)