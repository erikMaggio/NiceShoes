package com.example.ventazapas.data.model

data class ResponseUser(
    var debt: Int,
    var direction: String,
    var dni: String,
    var email: String,
    var favorite: List<String>,
    var id_edit: Int,
    var name: String,
    var number: String,
    var orders: List<String>,
    var shopping: List<String>,
    var state_account: Int,
    var type: String
)