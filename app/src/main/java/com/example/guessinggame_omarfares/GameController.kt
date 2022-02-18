package com.example.guessinggame_omarfares

import kotlin.random.Random

class GameController {
    private var lettersUsed: String = ""
    private lateinit var underscoreWord: String
    private lateinit var wordToGuess: String
    private val maxTries = 6
    private var currentTries = 0
    private var drawable: Int = R.drawable.hangman0

    fun startNewGame(): GameStatus {
        lettersUsed = ""
        currentTries = 0
        drawable = R.drawable.hangman6
        //select a random number
        val randomIndex = Random.nextInt(0, FruitsList.fruits.size)
        //Using the random number to pick a wordToGuess
        wordToGuess = FruitsList.fruits[randomIndex]
        // wordToGuess number of letters
        generateUnderscores(wordToGuess)
        return getGameStatus()
    }

    // generate "-" equal to the wordToGuess number of letters
    fun generateUnderscores(word: String) {
        val sb = StringBuilder()
        word.forEach { char ->
            sb.append("_")
            }

        underscoreWord = sb.toString()
    }

    //if a letter occurs multiple replace all.
    fun play(letter: Char): GameStatus {
        if (lettersUsed.contains(letter)) {
            return GameStatus.Running(underscoreWord, drawable,currentTries)
        }

        lettersUsed += letter
        val indexes = mutableListOf<Int>()

        //Match if any of the letters clicked is present in the "wordToGuess"
        wordToGuess.forEachIndexed { index, char ->
            if (char.equals(letter, true)) {
                indexes.add(index)
            }
        }

        var finalUnderscoreWord = "" + underscoreWord
        indexes.forEach { index ->
            val sb = StringBuilder(finalUnderscoreWord).also { it.setCharAt(index, letter) }
            finalUnderscoreWord = sb.toString()
        }

        if (indexes.isEmpty()) {
            currentTries++
        }

        underscoreWord = finalUnderscoreWord
        return getGameStatus()
    }

    // show the images according to the failure
    private fun getHangmanPixelArtDrawable(): Int {
        return when (currentTries) {
            0 -> R.drawable.hangman0
            1 -> R.drawable.hangman1
            2 -> R.drawable.hangman2
            3 -> R.drawable.hangman3
            4 -> R.drawable.hangman4
            5 -> R.drawable.hangman5
            6 -> R.drawable.hangman6
            else -> R.drawable.hangman6
        }
    }

    private fun getGameStatus(): GameStatus {
        if (underscoreWord.equals(wordToGuess, true)) {
            return GameStatus.Won(wordToGuess)
        }

        if (currentTries == maxTries) {
            return GameStatus.Lost(wordToGuess)
        }

        drawable = getHangmanPixelArtDrawable()
        return GameStatus.Running(underscoreWord,drawable,currentTries)
    }
}