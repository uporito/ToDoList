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
import com.example.todolist.model.ProfilListeTodo
import com.google.gson.Gson
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
    private lateinit var gson : Gson
    private lateinit var userListe : ProfilListeTodo
    private lateinit var jsonUserListe : String
    private lateinit var recyclerListe : RecyclerView
    private lateinit var titreListe : EditText
    private lateinit var btnOk : Button
    private lateinit var pseudo : String
    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var sharedPrefsEditor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.choix_list_activity)

        Log.i("appactivity", "CLA start")

//        // Liste test
//        val myDataset = listOf<ListeToDo>(
//                ListeToDo("Liste 1"),
//                ListeToDo("Liste 2"),
//                ListeToDo("Liste 3")
//        )

        // références vues et set click listener
        titreListe = findViewById<EditText>(R.id.nouvelle_liste_input)
        btnOk = findViewById<Button>(R.id.nouvelle_liste_btn_ok)
        btnOk.setOnClickListener(this)

        // init préférences
        sharedPrefs = android.preference.PreferenceManager.getDefaultSharedPreferences(this)
        sharedPrefsEditor = sharedPrefs.edit()

        // récupère pseudo actuel passé par l'extra
        val extras = this.intent.extras
        if (extras != null) {
            pseudo = extras.getString("pseudo").toString()
        }

        // récupère une représentation de l'utilisateur et de ses listes
        // à partir d'un json en utilisant gson
        gson = Gson()
        jsonUserListe = sharedPrefs.getString(pseudo, "{\"login\" : $pseudo, \"mesListesToDo\" : []}").toString()
        userListe = gson.fromJson(jsonUserListe, ProfilListeTodo::class.java)

        getLists()

        // recycler view
        recyclerListe = findViewById<RecyclerView>(R.id.recycler_list)
        recyclerListe.adapter = ListAdapter(this, userListe.mesListesToDo, this)
        recyclerListe.layoutManager = LinearLayoutManager(this)

        // Set titre action bar à l'utilisateur actuel
        getSupportActionBar()?.setTitle(pseudo)

    }

    private fun getLists() {
        activityScope.launch {
            var hash = sharedPrefs.getString("hash", "nohash")
            var lists = DataProvider.getCurrentUserLists()
        }
    }

    /**
     * Gestion click sur le bouton ok
     * ajoute une nouvelle liste dont le titre est le texte du champ nouvelle liste
     */
    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.nouvelle_liste_btn_ok -> {
                    if (titreListe.text.toString().isNotEmpty()) {
                        userListe.mesListesToDo.add(ListeToDo(titreListe.text.toString(), mutableListOf()))
                        jsonUserListe = gson.toJson(userListe)
                        sharedPrefsEditor.putString(pseudo, jsonUserListe)
                        sharedPrefsEditor.commit()
                        recyclerListe.adapter = ListAdapter(this, userListe.mesListesToDo, this)
                    }
                }
            }
        }

    }

    /**
     * Gestion click sur une liste
     * envoie vers showListActivity de cette liste
     * avec le titre de la liste en extra
     */
    override fun onListClicked(list: ListeToDo) {
        intent = Intent(this, ShowListActivity::class.java)
            .apply { putExtra("titreListe", list.titre) }
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

//    private fun loadLists(recycler : RecyclerView) {
//        val accessToken = DataProvider.getAccessToken()
//
//        val lists = DataProvider.getListsFromApi(accessToken)
//    }
}