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
import com.example.todolist.adapter.ItemAdapter
import com.example.todolist.adapter.OnItemClickListener
import com.example.todolist.data.DataProvider
import kotlinx.coroutines.*

class ShowListActivity : AppCompatActivity(), View.OnClickListener, OnItemClickListener {

    private val activityScope = CoroutineScope(
        SupervisorJob()
                + Dispatchers.Main
                + CoroutineExceptionHandler { _, throwable ->
            Log.e("MainActivity", "CoroutineExceptionHandler : ${throwable.message}")
        }
    )

    // définition des variables dont on se sert pour définir les fonctions
    private lateinit var recyclerItem : RecyclerView
    private lateinit var adapter : ItemAdapter
    private lateinit var labelListe : String
    private lateinit var idListe : String
    private lateinit var descriptionItem : EditText
    private lateinit var btnOk : Button
    private lateinit var pseudo : String
    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var sharedPrefsEditor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.show_list_activity)

        Log.i("appactivity", "SLA start")

        // références vues et set click listener
        descriptionItem = findViewById<EditText>(R.id.nouvel_item_input)
        btnOk = findViewById<Button>(R.id.nouvel_item_btn_ok)
        btnOk.setOnClickListener(this)

        // init préférences
        sharedPrefs = android.preference.PreferenceManager.getDefaultSharedPreferences(this)
        sharedPrefsEditor = sharedPrefs.edit()

        // Récupère le label et l'id de la liste
        pseudo = sharedPrefs.getString("pseudo", "none").toString()
        val extras = this.intent.extras
        if (extras != null) {
            //pseudo = extras.getString("pseudo").toString()
            labelListe = extras.getString("labelListe").toString()
            idListe = extras.getString("idListe").toString()
        }

        // recycler view
        recyclerItem = findViewById<RecyclerView>(R.id.recycler_item)
        adapter = ItemAdapter(this, this)
        recyclerItem.adapter = adapter
        recyclerItem.layoutManager = LinearLayoutManager(this)

        getItems()

        // Set titre action bar à la liste actuelle
        getSupportActionBar()?.setTitle(labelListe)

    }

    /**
     * Récupération des listes de l'utilisateur actuel auprès de l'API
     * et actualisation de l'affichage
     */
    private fun getItems() {
        try {
            activityScope.launch {
                var hash = sharedPrefs.getString("hash", "nohash").toString()
                var items = DataProvider.getListItems(hash, idListe)
                adapter.showData(items)
                recyclerItem.visibility = View.VISIBLE
            }
        } catch(e: Exception){
            Toast.makeText(this@ShowListActivity, "${e.message} ", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Gestion click sur le bouton ok
     * ajoute un nouvel item dont la description est le texte du champ nouvel item
     */
    override fun onClick(v: View?) {
        try {
            activityScope.launch {
                var hash = sharedPrefs.getString("hash", "nohash").toString()
                var label = descriptionItem.text.toString()
                DataProvider.newItem(hash, idListe, label)
                getItems()
            }
        } catch(e: Exception){
            Toast.makeText(this@ShowListActivity, "${e.message} ", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Gestion click sur un item
     * change l'état de la cb
     */
    override fun onItemClicked(v: View, pos: Int) {
        try {
            activityScope.launch {
                var hash = sharedPrefs.getString("hash", "nohash").toString()
                var items = DataProvider.getListItems(hash, idListe)
                val item = items[pos]
                if (item.checked == "1") { DataProvider.checkItem(hash, idListe, item.id, "0") }
                else { DataProvider.checkItem(hash, idListe, item.id, "1") }
            }
        } catch(e: Exception){
            Toast.makeText(this@ShowListActivity, "${e.message} ", Toast.LENGTH_SHORT).show()
        }
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