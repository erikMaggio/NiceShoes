package com.example.ventazapas.data.fireStore

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.ventazapas.data.model.ResponseShoes
import com.example.ventazapas.data.model.ResponseUser

interface FireStoreService {

    fun addUser(
        title: String,
        debt: Int,
        direction: String,
        dni: String,
        email: String,
        favorite: List<String>,
        idEdit: Int,
        name: String,
        number: String,
        orders: List<String>,
        shopping: List<String>,
        state_account: Int,
        type: String
    ): String

    fun getUser(title: String): MutableLiveData<ResponseUser>

    fun addShoes(
        code: String,
        color: String,
        description: String,
        discount_rate: String,
        gender: String,
        group: String,
        id: String,
        image: List<String>,
        name: String,
        offer_price: Int,
        price: Int,
        state_offer: Boolean,
        waist: String,
        avaiable:Boolean
    )

    fun getShoesById(id: String): MutableLiveData<ResponseShoes>

    fun getAllShoes(): MutableLiveData<List<ResponseShoes>>

    fun deleteShoes(id: String,owner: LifecycleOwner): MutableLiveData<Boolean>

    fun getListShoesByOffert(): MutableLiveData<List<ResponseShoes>>
}