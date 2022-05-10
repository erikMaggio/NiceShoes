package com.example.ventazapas.utils

import android.content.Context

class Preferences (val context: Context) {

    private val KEY_ACCESS_TOKEN = "type_login"
    private val KEY_USER_EMAIL = "user_mail"
    private val KEY_FIREBASE_TOKEN = "user_name"
    private val LAST_ID= "last id"

    private val storage = context.getSharedPreferences("com.niceShoes.", Context.MODE_PRIVATE)

    fun saveUserEmail(user: String) {
        storage.edit().putString(this.KEY_USER_EMAIL, user).apply()
    }

    fun getUserEmail(): String {
        return storage.getString(this.KEY_USER_EMAIL, "")!!
    }

    fun clear() {
        storage.edit().clear().apply()
    }

    fun saveFireBaseAuth(name:String){
        storage.edit().putString(this.KEY_FIREBASE_TOKEN,name).apply()
    }

    fun getFireBaseAuth(): String {
        return storage.getString(this.KEY_FIREBASE_TOKEN,"")!!
    }

    fun saveAccessToken(login: String) {
        storage.edit().putString(this.KEY_ACCESS_TOKEN,login).apply()
    }

    fun getAccessToken(): String {
        return storage.getString(this.KEY_ACCESS_TOKEN,"")!!
    }
    fun saveLastID(id:String){
        storage.edit().putString(this.LAST_ID,id).apply()
    }

    fun getLastID():String{
        return storage.getString(this.LAST_ID,"")!!
    }


}