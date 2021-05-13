package com.example.ddtracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EncounterAdapter(private val exampleList: List<Creature>,
                       private val listener: OnItemClickListener) :
    RecyclerView.Adapter<EncounterAdapter.ExampleViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.encounter_list_layout,
        parent, false)

       return ExampleViewHolder(itemView)
    }



    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {

        val currentItem = exampleList[position]
        if (currentItem.isActive)
        {
            holder.imageView?.setImageResource(R.drawable.ic_initiative_arrow)
            //setImageResource(R.drawable.active_initiative)
            //holder.imageView?.setBackgroundColor(Color.YELLOW)
        }
        else
        {
            holder.imageView?.setImageResource(R.drawable.ic_baseline_hourglass_bottom_24)
            //holder.imageView?.setBackgroundColor(Color.WHITE)
        }
        holder.textView1.text = currentItem.creatureName
        holder.textView2.text = currentItem.initiative.toString()
        holder.currentHealthText.text = currentItem.currentHealth.toString()
        holder.maxHealthText.text = currentItem.maxHealth.toString()

    }

    override fun getItemCount() = exampleList.size

   inner class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener{
       val imageView: ImageView? = itemView.findViewById(R.id.initiative_icon)
       val textView1: TextView = itemView.findViewById(R.id.text_view_1)
       val textView2: TextView = itemView.findViewById(R.id.text_view_2)
       val currentHealthText: TextView = itemView.findViewById(R.id.text_view_current_health)
       val maxHealthText: TextView = itemView.findViewById(R.id.text_view_max_health)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    interface  OnItemClickListener{
        fun onItemClick(position: Int)
    }

}