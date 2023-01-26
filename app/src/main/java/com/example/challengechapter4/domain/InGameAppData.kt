package com.example.challengechapter4.domain

data class InGameAppData(
    val chosen: Map<String, String> = mapOf(Pair("player", ""), Pair("computer", "")),
    val state: GameState = GameState.START,
    val result: String = ""
)