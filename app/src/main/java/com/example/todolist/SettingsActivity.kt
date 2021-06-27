package com.example.todolist

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceActivity
import androidx.preference.Preference


class SettingsActivity : PreferenceActivity(), SharedPreferences.OnSharedPreferenceChangeListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.root_preferences)
    }

    // change le summary du setting pseudo pour afficher le nouveau pseudo
    // ne marche pas
    override fun onSharedPreferenceChanged(sharedPrefs: SharedPreferences, key: String) {
        val pref : android.preference.Preference? = findPreference(key)
        if (pref != null) {
            pref.summary = sharedPrefs.getString(key, "")
        }

    }

}
