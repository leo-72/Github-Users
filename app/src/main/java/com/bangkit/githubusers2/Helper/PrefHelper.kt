package com.bangkit.githubusers2.Helper

import android.content.Context
import android.content.SharedPreferences

class PrefHelper(context: Context) {

    private val KEY_PREF = "darkMode"
    private var sharedPreferences : SharedPreferences
    val editor : SharedPreferences.Editor

    init {
        sharedPreferences = context.getSharedPreferences(KEY_PREF, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    fun put(key: String, value: Boolean){
        editor.putBoolean(key, value).apply()
    }

    fun getBoolean(key: String): Boolean{
        return sharedPreferences.getBoolean(key, false)
    }
}