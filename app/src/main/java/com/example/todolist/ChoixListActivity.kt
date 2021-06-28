package com.example.todolist

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
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.adapter.ListAdapter
import com.example.todolist.adapter.OnListClickListener
import com.example.todolist.data.DataProvider
import com.example.todolist.model.ListeToDo
import kotlinx.coroutines.*

class ChoixListActivity : AppCompatActivity(), View.OnClickListener, OnListClickListener {

    private val activityScope = CoroutineScope(
        SupervisorJob()
                + Dispatchers.Main
                + CoroutineExceptionHandler { _, throwable ->
            Log.e("MainActivity", "CoroutineExceptionHandler : ${throwable.message}")
        }
    )

    // définition des variables dont on se sert pour définir les fonctions
    private lateinit var recyclerListe : RecyclerView
    private lateinit var adapter : ListAdapter
    private lateinit var titreListe : EditText
    private lateinit var btnOk : Button
    private lateinit var pseudo : String
    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var sharedPrefsEditor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.choix_list_activity)

        Log.i("appactivity", "CLA start")

        // références vues et set click listener
        titreListe = findViewById<EditText>(R.id.nouvelle_liste_input)
        btnOk = findViewById<Button>(R.id.nouvelle_liste_btn_ok)
        btnOk.setOnClickListener(this)

        // init préférences
        sharedPrefs = android.preference.PreferenceManager.getDefaultSharedPreferences(this)
        sharedPrefsEditor = sharedPrefs.edit()

        // récupère le pseudo actuel dans les shared preferences
        pseudo = sharedPrefs.getString("pseudo", "nopseudo").toString()

        // recycler view
        recyclerListe = findViewById<RecyclerView>(R.id.recycler_list)
        adapter = ListAdapter(this, this)
        recyclerListe.adapter = adapter
        recyclerListe.layoutManager = LinearLayoutManager(this)

        getLists()

        // Set titre action bar à l'utilisateur actuel
        getSupportActionBar()?.setTitle(pseudo)

    }

    /**
     * Récupération des listes de l'utilisateur actuel auprès de l'API
     * et actualisation de l'affichage
     */
    private fun getLists() {
        try {
            activityScope.launch {
                var hash = sharedPrefs.getString("hash", "nohash").toString()
                var lists = DataProvider.getCurrentUserLists(hash)
                adapter.showData(lists)
                recyclerListe.visibility = View.VISIBLE

            }
        } catch(e: Exception){
        Toast.makeText(this@ChoixListActivity, "${e.message} ", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Gestion click sur le bouton ok
     * ajoute une nouvelle liste dont le titre est le texte du champ nouvelle liste
     */
    override fun onClick(v: View?) {
        try {
            activityScope.launch {
                var hash = sharedPrefs.getString("hash", "nohash").toString()
                var label = titreListe.text.toString()
                DataProvider.newList(hash, label)
                getLists()
            }
        } catch(e: Exception){
            Toast.makeText(this@ChoixListActivity, "${e.message} ", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Gestion click sur une liste
     * envoie vers showListActivity de cette liste
     * avec le titre de la liste en extra
     */
    override fun onListClicked(list: ListeToDo) {
        intent = Intent(this, ShowListActivity::class.java)
            .apply { putExtra("labelListe", list.label) }
            .apply { putExtra("idListe", list.id) }
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