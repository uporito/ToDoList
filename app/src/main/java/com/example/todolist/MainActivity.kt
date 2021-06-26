package com.example.todolist

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.adapter.ListAdapter
import com.example.todolist.data.Datasource
import com.example.todolist.model.ListeToDo

const val EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE"

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var sharedPreference : SharedPreferences
    private lateinit var sharedPreferenceEditor : SharedPreferences.Editor
    private lateinit var textPseudo : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // bouton OK
        val btnOk = findViewById<Button>(R.id.pseudo_btn_ok)
        val textPseudo = findViewById<TextView>(R.id.pseudo_input)
        btnOk.setOnClickListener(this)

        // sauvegarde du pseudo
        val sharedPreference : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val sharedPreferenceEditor : SharedPreferences.Editor

    }

    /**
     * Gestion du bouton OK
     */
    override fun onClick(v: View?) {
        // TODO:sauvegarder le pseudo dans les préférences
        //with (sharedPreferenceEditor) {
        //    putString("pseudo", textPseudo.text as String?)
        //    apply()
        //}

        // lance ChoixListActivity
        val intent = Intent(this, ChoixListActivity::class.java)
                // .apply { putExtra(EXTRA_MESSAGE, "msg")}
        startActivity(intent)
    }


    /**
     * Action Bar
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        // pour l'instant settings est le seul cas
        R.id.action_settings -> {
            startActivity(Intent(this, SettingsActivity::class.java))
            true
        }
        else -> {
            // exception
            super.onOptionsItemSelected(item)
        }
    }

}