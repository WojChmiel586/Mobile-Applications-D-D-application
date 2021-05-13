package com.example.ddtracker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class CreatureEdit : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creature_edit)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        val finishButton: Button = findViewById(R.id.DoneEditingButton)
        val nameText : EditText = findViewById(R.id.NameEdit)
        val initiativeNumber: TextView = findViewById(R.id.InitativeNumber)
        val currentHealth:TextView = findViewById(R.id.CurrentHealth)
        val maxHealth: TextView = findViewById(R.id.MaxHealth)
        val additionalInfo: TextView = findViewById(R.id.Notes)

        var creatureActive: Boolean = false
        var index: Int = -1


        //EDITING EXISTING CREATURE
        if (intent.getBooleanExtra("IS_EDITING", false))
        {
            title = "EDIT CREATURE"
            val creature : Creature = intent.getSerializableExtra("PASSED_CREATURE") as Creature
            nameText.setText(creature.creatureName)
            initiativeNumber.text = creature.initiative.toString()
            currentHealth.text = creature.currentHealth.toString()
            maxHealth.text = creature.maxHealth.toString()
            additionalInfo.text = creature.additionalNotes
            creatureActive = creature.isActive
            index = intent.getIntExtra("ARRAY_INDEX", -1)
        }
        //CREATING NEW CREATURE
        else
        {
            title = "CREATE CREATURE"

        }


        finishButton.setOnClickListener {


            if (maxHealth.text.toString().isEmpty() || nameText.text.toString().isBlank())
            {
                if (nameText.text.toString().isBlank())
                {
                    //nameText.setBackgroundColor(Color.RED)
                    nameText.error = "This field can not be blank"
                    nameText.requestFocus()
                }
                if (maxHealth.text.toString().isEmpty())
                {
                    maxHealth.error = "Input valid health amount"
                }
            }
            else
            {
                if (currentHealth.text.toString().isBlank())
                {
                    currentHealth.text = maxHealth.text.toString()
                }

                //SENDING BACK EDITED CREATURE
                if (intent.getBooleanExtra("IS_EDITING", false)) {
                    val returnIntent = Intent()
                    if (initiativeNumber.text == null) {
                        initiativeNumber.text = Random.nextInt(21).toString()
                    }
                    if(currentHealth.text.toString().toInt() > maxHealth.text.toString().toInt())
                    {
                        currentHealth.text = maxHealth.text
                    }
                    val returnInitiative: Int
                    if (initiativeNumber.text.toString().isBlank())
                    {
                        returnInitiative = Random.nextInt(21) + 1
                    }
                    else {
                        returnInitiative = initiativeNumber.text.toString().toInt()
                    }

                    val newCreature = Creature(
                        nameText.text.toString(),
                        returnInitiative,
                        0,
                        maxHealth.text.toString().toInt(),
                        currentHealth.text.toString().toInt(),
                        additionalInfo.text.toString(),
                        creatureActive

                    )
                    returnIntent.putExtra("NEW_CREATURE", newCreature)
                    if (index != -1) {
                        returnIntent.putExtra("EDITED_INDEX", index)
                    }
                    setResult(Activity.RESULT_OK, returnIntent)
                    finish()
                }
                //SENDING BACK NEW CREATURE
                else {
                    val returnIntent = Intent()
                    val returnInitiative: Int
                    if(currentHealth.text.toString().toInt() > maxHealth.text.toString().toInt())
                    {
                        currentHealth.text = maxHealth.text
                    }
                    if (initiativeNumber.text.toString().isBlank())
                    {
                        returnInitiative = Random.nextInt(21) + 1
                    }
                    else {
                        returnInitiative = initiativeNumber.text.toString().toInt()
                    }
                    val newCreature = Creature(
                        nameText.text.toString(),
                        returnInitiative,
                        0,
                        maxHealth.text.toString().toInt(),
                        currentHealth.text.toString().toInt(),
                        additionalInfo.text.toString(),
                        false
                    )
                    returnIntent.putExtra("NEW_CREATURE", newCreature)
                    setResult(Activity.RESULT_OK, returnIntent)
                    finish()
                }

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_encounter_activity, menu)
        return  true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.action_roll_dice) {
            startActivity(Intent(this, DiceRoller::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}