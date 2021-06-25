package com.are.imagefinder.data.pref

import android.content.Context
import android.content.SharedPreferences
import com.are.imagefinder.utils.Constants
import javax.inject.Inject

class AppPreferences @Inject constructor(context: Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE)

    fun setString(key: String?, stringToSave: String?) {
        preferences.edit().putString(key, stringToSave).apply()
    }

    fun getString(key: String?, defaultValue: String?): String? {
        return preferences.getString(key, defaultValue)
    }

    operator fun contains(key: String?): Boolean {
        return preferences.contains(key)
    }

    /**
     * Remove a Preference by Key
     * @param key String Key
     */
    fun removeAPreferences(key: String?) {
        preferences.edit().remove(key).apply()
    }

}