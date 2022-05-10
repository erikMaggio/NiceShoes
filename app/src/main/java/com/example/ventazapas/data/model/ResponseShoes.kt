package com.example.ventazapas.data.model

data class ResponseShoes(
    val code: String,
    val color: String,
    val description: String,
    val discount_rate: String,
    val gender: String,
    val group: String,
    val id: Int,
    val image: List<String>,
    val name: String,
    val offer_price: Int,
    val price: Int,
    val state_offer: Boolean,
    val waist: String,
    val available:Boolean
)