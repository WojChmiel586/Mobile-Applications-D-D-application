package com.example.ddtracker

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random


class EncounterActivity : AppCompatActivity(), EncounterAdapter.OnItemClickListener {
    //private var exampleList = generateDummyList(10)
    private val exampleList = ArrayList<Creature>()
    private val adapter = EncounterAdapter(exampleList, this)

    private var encounterStarted: Boolean = false
    private var encounterIndex: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_encounter)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        //var currentEncounter = Encounter(null, ArrayList())
        title = "New Encounter"
        val recycler_view = findViewById<RecyclerView>(R.id.recycler_view)
        recycler_view.adapter = adapter
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recycler_view)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
        exampleList.sortByDescending { it.initiative}

        if (exampleList.size > 0)
        {
            exampleList[0].isActive = false
            recycler_view[1].setBackgroundColor(Color.BLUE)
            adapter.notifyItemChanged(0)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_encounter_activity, menu)
        return  true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.action_roll_dice)
        {
            startActivity(Intent(this, DiceRoller::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun insertItem(view: View)
    {
        val intent = Intent(this,CreatureEdit::class.java)
        startActivityForResult(intent,12)
    }


    fun startEncounter(view: View)
    {
        if (encounterStarted)
        {
            if (exampleList.size < 2)
            {
                Toast.makeText(
                    this,
                    "Not enough creatures to continue encounter",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
            exampleList[encounterIndex].isActive = false
            adapter.notifyItemChanged(encounterIndex)
            if (encounterIndex + 1 >= exampleList.size)
            {
                encounterIndex = 0
                exampleList[encounterIndex].isActive = true
            }
            else
            {
                encounterIndex++
                exampleList[encounterIndex].isActive = true
            }
            adapter.notifyItemChanged(encounterIndex)
        }
        else {
            if (exampleList.size < 2)
            {
                Toast.makeText(this, "Add more creatures to the encounter", Toast.LENGTH_SHORT).show()
            }
            else
            {
                encounterStarted = true
                val button: Button = findViewById(R.id.StartEncounterButton)
                button.text = getString(R.string.Next_Turn)
                for (item in exampleList) {
                    item.isActive = false
                }
                exampleList[0].isActive = true
                encounterIndex = 0
                adapter.notifyDataSetChanged()
            }
        }

    }

    override fun onItemClick(position: Int)
    {
        editCreature(position)
    }

    //Unused in current implementation
    private fun generateDummyList(size: Int): ArrayList<Creature> {
        val list = ArrayList<Creature>()

        for (i in 0 until size) {
            val drawable = R.drawable.active_initiative
            val initiative = Random.nextInt(21) + Random.nextInt(6)
            val creature = Creature("Steven $i", initiative, 2, 30, 30)
            list += creature
        }

        return list
    }

    private fun editCreature(arrayIntex: Int)
    {
        val clickedItem = exampleList[arrayIntex]
        val intent = Intent(this, CreatureEdit::class.java)
        intent.putExtra("PASSED_CREATURE",clickedItem)
        intent.putExtra("IS_EDITING", true)
        intent.putExtra("ARRAY_INDEX", arrayIntex)
        startActivityForResult(intent, 24)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 12)
        {
            if ( resultCode == Activity.RESULT_OK)
            {
                if (data == null)
                {
                    Toast.makeText(this, "NO DATA",Toast.LENGTH_SHORT).show()
                }
                else
                {
                    val returnedCreature: Creature = data.getSerializableExtra("NEW_CREATURE") as Creature
                    exampleList.add(0,returnedCreature)
                    exampleList.sortByDescending { it.initiative}

                    adapter.notifyDataSetChanged()
                }
            }


        }
        if (requestCode == 24)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                if (data != null)
                {
                    val returnedCreature: Creature = data.getSerializableExtra("NEW_CREATURE") as Creature
                    val returnedIndex: Int = data.getIntExtra("EDITED_INDEX", -1)
                    if (returnedIndex != -1) {
                        exampleList[returnedIndex] = returnedCreature
                        exampleList.sortByDescending { it.initiative}
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }
    private val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT)
    {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, position: Int) {

            if (!exampleList[viewHolder.adapterPosition].isActive)
            {
                exampleList.removeAt(viewHolder.adapterPosition)
            }
            adapter.notifyDataSetChanged()
        }

    }
}