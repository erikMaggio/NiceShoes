package com.example.ventazapas.data.fireStore

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.ventazapas.data.model.ResponseShoes
import com.example.ventazapas.data.model.ResponseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

import androidx.lifecycle.LifecycleOwner
import com.example.ventazapas.data.model.ResponseOrders

class FireStoreImp : FireStoreService {
    val mStorage = FirebaseStorage.getInstance().getReference()
    private val fireStore = FirebaseFirestore.getInstance()
    private val liveUser = MutableLiveData<ResponseUser>()
    private val liveShoes = MutableLiveData<ResponseShoes>()
    private val liveDelete = MutableLiveData<Boolean>()
    private val liveAllOffertShoes = MutableLiveData<List<ResponseShoes>>()
    private val liveAllShoes = MutableLiveData<List<ResponseShoes>>()
    private val liveListShoesById = MutableLiveData<List<ResponseShoes>>()
    private val liveImage = MutableLiveData<String>()
    private val liveListOrders = MutableLiveData<List<ResponseOrders>>()
    private val liveOrder = MutableLiveData<ResponseOrders>()

    override fun addUser(
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
    ): String {
        if (fireStore.collection("user").document(title).set(
                hashMapOf(
                    "debt" to debt,
                    "direction" to direction,
                    "dni" to dni,
                    "email" to email,
                    "favorite" to favorite,
                    "id_edit" to idEdit,
                    "name" to name,
                    "number" to number,
                    "orders" to orders,
                    "shopping" to shopping,
                    "state_account" to state_account,
                    "type" to type

                )
            ).isCanceled
        ) {
            return "success"

        } else {
            return "error"
        }
    }


    override fun getUser(title: String): MutableLiveData<ResponseUser> {
        fireStore.collection("user").document(title).get().addOnSuccessListener {
            if (!it.data.isNullOrEmpty()) {
                liveUser.postValue(
                    ResponseUser(
                        it.get("debt").toString().toInt(),
                        it.get("direction").toString(),
                        it.get("dni").toString(),
                        it.get("email").toString(),
                        it.get("favorite") as List<String>,
                        it.get("id_edit").toString().toInt(),
                        it.get("name").toString(),
                        it.get("number").toString(),
                        it.get("orders") as List<String>,
                        it.get("shopping") as List<String>,
                        it.get("state_account").toString().toInt(),
                        it.get("type").toString()
                    )
                )
            } else {
                liveUser.postValue(
                    ResponseUser(
                        0,
                        "",
                        "",
                        "",
                        listOf(),
                        0,
                        "empty",
                        "",
                        listOf(),
                        listOf(),
                        0,
                        ""
                    )
                )
            }

        }.addOnFailureListener {
            Log.d("res", "error")

        }
        return liveUser
    }


    override fun addShoes(
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
        avaiable: Boolean
    ) {
        fireStore.collection("shoes").document(id).set(
            hashMapOf(
                "code" to code,
                "color" to color,
                "description" to description,
                "discount_rate" to discount_rate,
                "gender" to gender,
                "group" to group,
                "id" to id,
                "image" to image,
                "name" to name,
                "offer_price" to offer_price,
                "price" to price,
                "state_offer" to state_offer,
                "waist" to waist,
                "available" to avaiable
            )
        )
    }

    override fun getShoesById(id: String): MutableLiveData<ResponseShoes> {
        fireStore.collection("shoes").document(id).get()
            .addOnSuccessListener {

                if (it.data?.get("available").toString().toBoolean()) {
                    if (!it.data.isNullOrEmpty()) {
                        liveShoes.postValue(
                            ResponseShoes(
                                it.get("code").toString(),
                                it.get("color").toString(),
                                it.get("description").toString(),
                                it.get("discount_rate").toString(),
                                it.get("gender").toString(),
                                it.get("group").toString(),
                                it.get("id").toString().toInt(),
                                it.get("image") as List<String>,
                                it.get("name").toString(),
                                it.get("offer_price").toString().toInt(),
                                it.get("price").toString().toInt(),
                                it.get("state_offer").toString().toBoolean(),
                                it.get("waist").toString(),
                                it.get("available").toString().toBoolean()
                            )
                        )
                    } else {
                        liveShoes.postValue(
                            ResponseShoes(
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                0,
                                listOf(),
                                "empty",
                                0,
                                0,
                                false,
                                "",
                                false
                            )
                        )
                    }

                }
            }
        return liveShoes
    }

    override fun getAllShoes(): MutableLiveData<List<ResponseShoes>> {
        fireStore.collection("shoes").whereEqualTo("state_offer", false)
            .whereEqualTo("available", true).get()
            .addOnSuccessListener {
                val list = mutableListOf<ResponseShoes>()
                for (i in it.documents) {
                    list.add(
                        ResponseShoes(
                            i.get("code").toString(),
                            i.get("color").toString(),
                            i.get("description").toString(),
                            i.get("discount_rate").toString(),
                            i.get("gender").toString(),
                            i.get("group").toString(),
                            i.get("id").toString().toInt(),
                            i.get("image") as List<String>,
                            i.get("name").toString(),
                            i.get("offer_price").toString().toInt(),
                            i.get("price").toString().toInt(),
                            i.get("state_offer").toString().toBoolean(),
                            i.get("waist").toString(),
                            i.get("available").toString().toBoolean()
                        )
                    )
                }
                liveAllShoes.postValue(list)
            }
        return liveAllShoes
    }


    override fun deleteShoes(id: String, owner: LifecycleOwner): MutableLiveData<Boolean> {
        getShoesById(id).observe(owner, {
            addShoes(
                it.code,
                it.color,
                it.description,
                it.discount_rate,
                it.gender,
                it.group,
                it.id.toString(),
                it.image,
                it.name,
                it.offer_price,
                it.price,
                it.state_offer,
                it.waist,
                false
            )
        })

        return liveDelete
    }

    fun getImage(image: Uri): MutableLiveData<String> {
        val FilePath: StorageReference =
            mStorage.child("Fotos1")!!.child(image.getLastPathSegment()!!)
        image.let { FilePath.putFile(it) }
            ?.addOnSuccessListener { //Esto seria para descargar su token de enlace y poder acceder a ella
                //Si no lo quieres poner no hace falta

                FilePath.downloadUrl.addOnSuccessListener {
                    Log.d("responseFire", it.toString())
                    liveImage.postValue(it.toString())
                }
            }
        return liveImage
    }

    override fun getListShoesByOffert(): MutableLiveData<List<ResponseShoes>> {
        fireStore.collection("shoes").whereEqualTo("state_offer", true).whereEqualTo("avaiable", true).get().addOnSuccessListener {
            val list = mutableListOf<ResponseShoes>()
            for (i in it.documents) {
                list.add(
                    ResponseShoes(
                        i.get("code").toString(),
                        i.get("color").toString(),
                        i.get("description").toString(),
                        i.get("discount_rate").toString(),
                        i.get("gender").toString(),
                        i.get("group").toString(),
                        i.get("id").toString().toInt(),
                        i.get("image") as List<String>,
                        i.get("name").toString(),
                        i.get("offer_price").toString().toInt(),
                        i.get("price").toString().toInt(),
                        i.get("state_offer").toString().toBoolean(),
                        i.get("waist").toString(),
                        true
                    )
                )
            }
            liveAllOffertShoes.postValue(list)
        }
        return liveAllOffertShoes
    }

    fun addFavoriteToUser(email: String, id: String, owner: LifecycleOwner) {
        var newIds = mutableListOf<String>()
        getUser(email).observe(owner, {
            if (it.favorite.isNotEmpty()) {
                for (i in it.favorite) {
                    if (!i.equals("") && !newIds.contains(i)) {
                        newIds.add(i)
                    }

                }
            }
            newIds.add(id)
            fireStore.collection("user").document(email).set(
                hashMapOf(
                    "debt" to it.debt,
                    "direction" to it.direction,
                    "dni" to it.dni,
                    "email" to it.email,
                    "favorite" to newIds,
                    "id_edit" to it.id_edit,
                    "name" to it.name,
                    "number" to it.number,
                    "orders" to it.orders,
                    "shopping" to it.shopping,
                    "state_account" to it.state_account,
                    "type" to it.type
                )
            )
        })
    }

    fun deleteFavoriteToUser(email: String, id: String, owner: LifecycleOwner) {
        var newIds2 = mutableListOf<String>()
        getUser(email).observe(owner, {
            newIds2 = it.favorite as MutableList<String>
            if (newIds2.contains(id)) {
                newIds2.remove(id)
            }

            fireStore.collection("user").document(email).set(
                hashMapOf(
                    "debt" to it.debt,
                    "direction" to it.direction,
                    "dni" to it.dni,
                    "email" to it.email,
                    "favorite" to newIds2,
                    "id_edit" to it.id_edit,
                    "name" to it.name,
                    "number" to it.number,
                    "orders" to it.orders,
                    "shopping" to it.shopping,
                    "state_account" to it.state_account,
                    "type" to it.type
                )
            )
        })
    }

    fun getListShoesByIDs(listId: List<String>): MutableLiveData<List<ResponseShoes>> {
        val listTemp = mutableListOf<ResponseShoes>()
        for (i in listId) {
            fireStore.collection("shoes").whereEqualTo("id", i).whereEqualTo("avaiable", true).get()
                .addOnSuccessListener {
                    for (i in it.documents) {
                        listTemp.add(
                            ResponseShoes(
                                i.get("code").toString(),
                                i.get("color").toString(),
                                i.get("description").toString(),
                                i.get("discount_rate").toString(),
                                i.get("gender").toString(),
                                i.get("group").toString(),
                                i.get("id").toString().toInt(),
                                i.get("image") as List<String>,
                                i.get("name").toString(),
                                i.get("offer_price").toString().toInt(),
                                i.get("price").toString().toInt(),
                                i.get("state_offer").toString().toBoolean(),
                                i.get("waist").toString(),
                                true
                            )
                        )
                    }
                    liveListShoesById.value = listTemp
                }
        }
        return liveListShoesById
    }

    fun addMyOrdersUsers(email: String, id: String, owner: LifecycleOwner) {
        var newIds = mutableListOf<String>()
        getUser(email).observe(owner) {
            if (it.orders.isNotEmpty()) {
                for (i in it.orders) {
                    if (i != "" && !newIds.contains(i)) {
                        newIds.add(i)
                    }
                }
            }
            newIds.add(id)
            fireStore.collection("user").document(email).set(
                hashMapOf(
                    "debt" to it.debt,
                    "direction" to it.direction,
                    "dni" to it.dni,
                    "email" to it.email,
                    "favorite" to it.favorite,
                    "id_edit" to it.id_edit,
                    "name" to it.name,
                    "number" to it.number,
                    "orders" to newIds,
                    "shopping" to it.shopping,
                    "state_account" to it.state_account,
                    "type" to it.type
                )
            )
        }
    }

    fun deleteMyOrdersUsers(email: String, id: String, owner: LifecycleOwner) {
        var newID = mutableListOf<String>()
        getUser(email).observe(owner) {
            newID = it.orders as MutableList<String>
            if (newID.contains(id)) {
                newID.remove(id)
            }
            fireStore.collection("user").document(email).set(
                hashMapOf(
                    "debt" to it.debt,
                    "direction" to it.direction,
                    "dni" to it.dni,
                    "email" to it.email,
                    "favorite" to it.favorite,
                    "id_edit" to it.id_edit,
                    "name" to it.name,
                    "number" to it.number,
                    "orders" to newID,
                    "shopping" to it.shopping,
                    "state_account" to it.state_account,
                    "type" to it.type
                )
            )
        }
    }

    fun addOrders(id: String, email: String, owner: LifecycleOwner) {
        val temp = mutableListOf<String>()
        getOrderByID(email).observe(owner, {
            if (!it.ids.isNullOrEmpty()) {
                for (i in it.ids) {
                    temp.add(i)
                }
            }
            temp.add(id)
            fireStore.collection("shopping").document(it.email).set(
                hashMapOf(
                    "id" to temp,
                    "email" to email
                )
            )
        })

    }


    fun getAllOrders(): MutableLiveData<List<ResponseOrders>> {
        val temp = mutableListOf<ResponseOrders>()
        fireStore.collection("shopping").get().addOnSuccessListener {
            for (i in it.documents) {
                temp.add(
                    ResponseOrders(
                        i.get("id") as List<String>,
                        i.get("email").toString()
                    )
                )
            }
            liveListOrders.value = temp
        }
        return liveListOrders
    }

    fun getOrderByID(email: String): MutableLiveData<ResponseOrders> {
        fireStore.collection("shopping").document(email).get().addOnSuccessListener {
            if (it.data != null) {
                liveOrder.postValue(
                    ResponseOrders(
                        it.get("id") as List<String>,
                        it.get("email").toString()
                    )
                )
            }
        }
        return liveOrder
    }

    fun registerOrder(email: String) {
        fireStore.collection("shopping").document(email).set(
            hashMapOf(
                "id" to listOf<String>(),
                "email" to email
            )
        )
    }
}
