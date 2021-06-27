package com.example.todolist

import android.content.ClipDescription
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.adapter.ItemAdapter
import com.example.todolist.adapter.ListAdapter
import com.example.todolist.adapter.OnItemClickListener
import com.example.todolist.model.ItemToDo
import com.example.todolist.model.ListeToDo
import com.example.todolist.model.UserListeTodo
import com.google.gson.Gson

class ShowListActivity : AppCompatActivity(), View.OnClickListener, OnItemClickListener {

    // définition des variables dont on se sert pour définir les fonctions
    private lateinit var gson : Gson
    private lateinit var listeItem : ListeToDo
    private lateinit var jsonListeItem : String
    private lateinit var recyclerItem : RecyclerView
    private lateinit var titreListe : String
    private lateinit var descriptionItem : EditText
    private lateinit var btnOk : Button
    private lateinit var pseudo : String
    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var sharedPrefsEditor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.show_list_activity)

        // références vues et set click listener
        descriptionItem = findViewById<EditText>(R.id.nouvel_item_input)
        btnOk = findViewById<Button>(R.id.nouvel_item_btn_ok)
        btnOk.setOnClickListener(this)

        // init préférences
        sharedPrefs = android.preference.PreferenceManager.getDefaultSharedPreferences(this)
        sharedPrefsEditor = sharedPrefs.edit()

        // Récupère le pseudo et la liste et affiche les items
        pseudo = sharedPrefs.getString("pseudo", "none").toString()
        val extras = this.intent.extras
        if (extras != null) {
            //pseudo = extras.getString("pseudo").toString()
            titreListe = extras.getString("titreListe").toString()
        }

        // récupère une représentation de la liste et de ses items
        // à partir d'un json en utilisant gson
        gson = Gson()
        jsonListeItem = sharedPrefs.getString(Pair(pseudo, titreListe).toString(),
            "{\"listTitle\": $titreListe\", \"listItems\": []}"
        ).toString()
        Log.i("jsonliste1", jsonListeItem)
        listeItem = gson.fromJson(jsonListeItem, ListeToDo::class.java)

        // recycler view
        recyclerItem = findViewById<RecyclerView>(R.id.recycler_item)
        recyclerItem.adapter = ItemAdapter(this, listeItem.listItems, this)
        recyclerItem.layoutManager = LinearLayoutManager(this)

        // Set titre action bar à la liste actuelle
        getSupportActionBar()?.setTitle(titreListe)

    }

    /**
     * Gestion click sur le bouton ok
     * ajoute une nouvelle liste dont le titre est le texte du champ nouvelle liste
     */
    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.nouvel_item_btn_ok-> {
                    listeItem.listItems.add(ItemToDo(descriptionItem.text.toString()))
                    jsonListeItem = gson.toJson(listeItem)
                    Log.i("jsonliste1", jsonListeItem)
                    sharedPrefsEditor.putString(Pair(pseudo, titreListe).toString(), jsonListeItem)
                    sharedPrefsEditor.commit()
                    recyclerItem.adapter = ItemAdapter(this, listeItem.listItems, this)
                }
            }
        }
    }

    override fun onItemClicked(v: View, pos: Int) {
        val checkBox = v as CheckBox
        val item = listeItem.listItems[pos]
        item.etat = checkBox.isChecked
        jsonListeItem = gson.toJson(listeItem)
        Log.i("jsonliste1", jsonListeItem)
        sharedPrefsEditor.putString(Pair(pseudo, titreListe).toString(), jsonListeItem)
        sharedPrefsEditor.commit()
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