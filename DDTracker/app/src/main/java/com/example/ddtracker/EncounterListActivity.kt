package com.example.ddtracker

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class EncounterListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_encounter_list)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    }








/*    fun LoadEncounter()
    {
        var intent = Intent(this,EncounterActivity::class.java)

        startActivity(intent)

    }*/
}