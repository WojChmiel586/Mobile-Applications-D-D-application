package com.example.ddtracker

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        val encButton : Button = findViewById(R.id.EncounterButton)
        val encListButton: Button = findViewById(R.id.EncounterListButton)
        val diceRollerButton: Button = findViewById(R.id.DiceActivityButton)

        encButton.setOnClickListener{
            val intent = Intent(this,EncounterActivity::class.java)
            startActivity(intent)
        }

        encListButton.setOnClickListener{

            //HAVEN'T MANAGED TO IMPLEMENT THIS FUNCTIONALITY
            Toast.makeText(this, "Functionality unavailable", Toast.LENGTH_SHORT).show()
            //val intent = Intent(this, EncounterListActivity::class.java)
            //startActivity(intent)

        }

        diceRollerButton.setOnClickListener {
            val intent = Intent(this,DiceRoller::class.java)
            startActivity(intent)
        }

    }
}


