package com.example.guessinggame_omarfares

sealed class GameStatus {
    class Running(val underscoreWord: String,
                  val drawable: Int,
                  val currentTries : Int) : GameStatus()
    class Lost(val wordToGuess: String) : GameStatus()
    class Won(val wordToGuess: String) : GameStatus()
}