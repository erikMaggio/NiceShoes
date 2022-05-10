package com.example.ventazapas

import android.app.Application
import com.example.ventazapas.utils.Preferences

class AppNiceShoes : Application() {

    companion object {
        lateinit var preferences: Preferences
    }

    override fun onCreate() {
        super.onCreate()
        preferences = Preferences(applicationContext)
    }
}