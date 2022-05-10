package com.example.ventazapas.data.model

data class MockShoes (
    val name :String,
    val type : String,
    val price : Int,
    val offer : Boolean,
    val image : List<String>,
    val description: String,
    val waist : String,
    val color : String,
    val gender : Gender

)
enum class Gender {
    Masculino, Femenino, Unisex
}