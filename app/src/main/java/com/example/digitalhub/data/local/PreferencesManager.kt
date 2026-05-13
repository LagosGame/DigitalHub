package com.example.digitalhub.data.local

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("DigitalHubPrefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_USERNAME = "saved_username"
        private const val KEY_PASSWORD = "saved_password"
        private const val KEY_REMEMBER = "remember_credentials"
    }

    fun saveCredentials(username: String, password: String) {
        prefs.edit().apply {
            putString(KEY_USERNAME, username)
            putString(KEY_PASSWORD, password)
            putBoolean(KEY_REMEMBER, true)
            apply()
        }
    }

    fun clearCredentials() {
        prefs.edit().apply {
            remove(KEY_USERNAME)
            remove(KEY_PASSWORD)
            putBoolean(KEY_REMEMBER, false)
            apply()
        }
    }

    fun getSavedUsername(): String? = prefs.getString(KEY_USERNAME, null)
    fun getSavedPassword(): String? = prefs.getString(KEY_PASSWORD, null)
    fun shouldRemember(): Boolean = prefs.getBoolean(KEY_REMEMBER, false)
}