package com.example.challengechapter5.domain

data class InGameAppData(
    val chosen: Map<String, String> = mapOf(Pair("player", ""), Pair("computer", "")),
    val state: GameState = GameState.START,
    val result: String = ""
)