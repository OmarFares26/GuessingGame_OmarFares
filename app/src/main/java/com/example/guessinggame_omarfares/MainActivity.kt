package com.example.guessinggame_omarfares

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // direct toward the "game-page"
        playButton.setOnClickListener {
            //Move to the activity specified "GameActivity"
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }
    }
}