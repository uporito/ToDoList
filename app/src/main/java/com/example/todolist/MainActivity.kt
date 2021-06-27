package com.example.todolist

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
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

class MainActivity : AppCompatActivity(), View.OnClickListener {

    // définition des variables dont on se sert pour définir les fonctions
    private lateinit var textPseudo : TextView
    private lateinit var btnOk : Button
    private lateinit var sharedPrefs : SharedPreferences
    private lateinit var sharedPrefsEditor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // bouton OK
        textPseudo = findViewById<TextView>(R.id.pseudo_input)
        btnOk = findViewById<Button>(R.id.pseudo_btn_ok)
        btnOk.setOnClickListener(this)

        // init préférences
        sharedPrefs = android.preference.PreferenceManager.getDefaultSharedPreferences(this)
        sharedPrefsEditor = sharedPrefs.edit()
        textPseudo.text = sharedPrefs.getString("pseudo", "none")

    }

    /**
     * Gestion du bouton OK
     * écrase la valeur actuelle du pseudo
     * et lance la choixListeActivity correspondante
     */
    override fun onClick(v: View?) {
        // sauvegarde le pseudo dans les préférences
        sharedPrefs = android.preference.PreferenceManager.getDefaultSharedPreferences(this)
        sharedPrefsEditor = sharedPrefs.edit()
        sharedPrefsEditor.putString("pseudo", textPseudo.text.toString())
        sharedPrefsEditor.commit()


        // lance ChoixListActivity
        val bdl = Bundle()
        bdl.putString("pseudo", textPseudo.text.toString())
        val intent = Intent(this, ChoixListActivity::class.java)
            // .apply { putExtra("pseudo", textPseudo.text.toString())}
        intent.putExtras(bdl)
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