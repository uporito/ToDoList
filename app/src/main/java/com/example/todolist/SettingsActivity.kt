package com.example.todolist

import android.os.Bundle
import android.os.PersistableBundle
import android.preference.PreferenceActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat

class SettingsActivity : PreferenceActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.root_preferences)
    }
}
