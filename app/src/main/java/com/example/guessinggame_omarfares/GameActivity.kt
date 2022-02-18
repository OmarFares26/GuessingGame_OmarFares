package com.example.guessinggame_omarfares

import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.children
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity() {
    private val gameController = GameController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        //change the wordToGuess
        newRoundButton.setOnClickListener {
            startNewGame()
        }
        val gameState = gameController.startNewGame()
        updateUI(gameState)

        lettersLayout.children.forEach { letterView ->
            if (letterView is TextView) {
                letterView.setOnClickListener {
                    val gameState = gameController.play((letterView).text[0])
                    updateUI(gameState)
                    letterView.setBackgroundColor(Color.parseColor("#FFA37E9E"))
                }
            }
        }
    }



    private fun updateUI(gameStatus: GameStatus) {
        when (gameStatus) {
            is GameStatus.Lost -> endGame(gameStatus.wordToGuess)
            is GameStatus.Running -> {
                wordTextView.text = gameStatus.underscoreWord
                //show failed attempts
                failedAttempts.text = "Failed Attempts : ${gameStatus.currentTries} /6"
                imageView.setImageDrawable(ContextCompat.getDrawable(this, gameStatus.drawable))
            }
            is GameStatus.Won -> wonGame(gameStatus.wordToGuess)
        }
    }

    private fun endGame(wordToGuess: String) {
        wordTextView.text = wordToGuess
        imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.hangman6))
        lettersLayout.visibility = View.VISIBLE
        failedAttempts.text = "Failed Attempts : 6/6"


        //create AlertDialog
        val builder = AlertDialog.Builder(this)
        with(builder)
        {
            setTitle("Game over")
            setMessage("\n" +"The Fruit you failed to guess is : ${wordToGuess.capitalize()}" + "\n" + "\n" +"Try next time !!")
            setPositiveButton("OK", DialogInterface.OnClickListener { dialogInterface, i ->
                startNewGame()
            })
            //prevent dialog box from getting dismissed on outside touch use this
            setCancelable(false)
            show()
        }
    }

    private fun wonGame(wordToGuess: String) {
        wordTextView.text = wordToGuess
        lettersLayout.visibility = View.VISIBLE

        //create AlertDialog
        val builder = AlertDialog.Builder(this)
        with(builder)
        {
            setTitle("Congrats")
            setMessage("Go little rock star !!")
            setPositiveButton("OK", DialogInterface.OnClickListener { dialogInterface, i ->
                startNewGame()
            })
            //prevent dialog box from getting dismissed on outside touch use this
            setCancelable(false)
            show()
        }
    }

    private fun startNewGame() {
        val gameStatus = gameController.startNewGame()
        lettersLayout.visibility = View.VISIBLE
        lettersLayout.children.forEach { letterView ->
            if (letterView is TextView)(
                    letterView.setBackgroundColor(Color.parseColor("#9F4393"))
            )
        }
        updateUI(gameStatus)
    }

}