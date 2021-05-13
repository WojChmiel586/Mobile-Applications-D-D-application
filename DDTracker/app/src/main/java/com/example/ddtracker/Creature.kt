package com.example.ddtracker

import java.io.Serializable

data class Creature(
    var creatureName:String?,
    var initiative: Int, var initiativeBonus: Int,
    var maxHealth: Int,
    var currentHealth: Int,
    var additionalNotes: String = "",
    var isActive: Boolean = false) : Serializable{

}