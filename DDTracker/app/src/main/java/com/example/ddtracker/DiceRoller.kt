package com.example.ddtracker

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.lang.reflect.Field
import kotlin.random.Random

class DiceRoller : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dice_roller)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        title = "Dice Roller"

        val diceSpinner = findViewById<Spinner>(R.id.diceSpinner)
        val diceTypes: MutableList<String> = ArrayList()
        val rollDice: Button = findViewById(R.id.rollDiceButton)
        val showSeparateResults: CheckBox = findViewById(R.id.checkBox)
        val diceAmount: TextView = findViewById(R.id.diceAmount)
        val rollResult: TextView = findViewById(R.id.rollResult)
        var pickedDiceType: Int = 4
        diceTypes.add("D4")
        diceTypes.add("D6")
        diceTypes.add("D8")
        diceTypes.add("D10")
        diceTypes.add("D12")
        diceTypes.add("D20")
        diceTypes.add("D100")

        val adapter = ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item, diceTypes)
        diceSpinner.adapter = adapter
        rollDice.setOnClickListener {

            if (diceAmount.text.toString().isNotEmpty())
            {
                var sum: Int = 0;
                var separateResults: String = ""
                if (!showSeparateResults.isChecked)
                {
                    for (i in 1..diceAmount.text.toString().toInt()) {
                        sum += Random.nextInt(pickedDiceType) + 1
                    }
                    rollResult.text = sum.toString()
                }
                else
                {
                    for (i in 1..diceAmount.text.toString().toInt())
                    {
                        val res = (Random.nextInt(pickedDiceType) + 1)
                        sum += res
                        separateResults += res.toString() + " + "
                    }
                    rollResult.text = separateResults.removeSuffix(" + ") + " = " + sum
                }

            }
            else
            {
                diceAmount.error = "Input dice amount"
                diceAmount.requestFocus()
            }
        }


        diceSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                pickedDiceType = when(position)
                {
                    0 -> 4
                    1 -> 6
                    2 -> 8
                    3 -> 10
                    4 -> 12
                    5 -> 20
                    else -> 100
                }
            }


        }

        //limitDropDownHeight(diceSpinner)

    }

    fun limitDropDownHeight(spnTest: Spinner)
    {
        val popup: Field = Spinner::class.java.getDeclaredField("Popup")
        popup.isAccessible = true

        val popupWindow = popup.get(spnTest) as ListPopupWindow
        popupWindow.height = (200 * resources.displayMetrics.density).toInt()

    }

}