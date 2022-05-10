package com.example.ventazapas.data.datasource

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.ventazapas.data.fireStore.FireStoreImp
import com.example.ventazapas.data.model.Gender
import com.example.ventazapas.data.model.MockShoes
import com.example.ventazapas.data.model.ResponseOrders
import com.example.ventazapas.data.model.ResponseShoes

class ShoesDataSource {

    val fireStoreImp = FireStoreImp()

    val liveListOrders = MutableLiveData<ResponseOrders>()

// tendria que estar en repository
    fun getAllOrders(owner: LifecycleOwner): MutableLiveData<ResponseOrders> {
        var temp = mutableListOf<ResponseOrders>()
        fireStoreImp.getAllOrders().observe(owner, {
            for (i in it) {
                if (i.ids.isNotEmpty()) {
                    temp.add(i)
                }
            }
            liveListOrders.value

        })
        return liveListOrders

    }
}