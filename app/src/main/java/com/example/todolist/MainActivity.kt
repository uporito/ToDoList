package com.example.todolist

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.adapter.ListAdapter
import com.example.todolist.data.DataProvider
import com.example.todolist.model.ListeToDo
import com.google.gson.Gson
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val activityScope = CoroutineScope(
        SupervisorJob()
                + Dispatchers.Main
                + CoroutineExceptionHandler { _, throwable ->
            Log.e("MainActivity", "CoroutineExceptionHandler : ${throwable.message}")
        }
    )

    // définition des variables dont on se sert pour définir les fonctions
    private lateinit var textPseudo : TextView
    private lateinit var textPass : TextView
    private lateinit var btnOk : Button
    private lateinit var sharedPrefs : SharedPreferences
    private lateinit var sharedPrefsEditor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.i("appactivity", "app start")


        // bouton OK
        textPseudo = findViewById<TextView>(R.id.pseudo_input)
        textPass = findViewById<TextView>(R.id.pass_input)
        btnOk = findViewById<Button>(R.id.pseudo_btn_ok)
        btnOk.setOnClickListener(this)
        btnOk.setEnabled(isOnline())


        // init préférences
        sharedPrefs = android.preference.PreferenceManager.getDefaultSharedPreferences(this)
        sharedPrefsEditor = sharedPrefs.edit()
        textPseudo.text = sharedPrefs.getString("pseudo", "none")

        var gson = Gson()
        //var lists = DataProvider.getListsFromApi()
        //var jsonLists = gson.toJson(lists)
        //Log.d("apirequest", jsonLists)

    }

    /**
     * Gestion du bouton OK
     * écrase la valeur actuelle du pseudo
     * et lance la choixListeActivity correspondante
     */
    override fun onClick(v: View?) {

        login()

        // lance ChoixListActivity en lui passant le pseudo actuel
        val intent = Intent(this, ChoixListActivity::class.java)
            // .apply { putExtra("pseudo", textPseudo.text.toString())}
        startActivity(intent)

    }

    // réalise l'authetification et la révupération du hash auprès de l'api
    // dans une nouvelle coroutine
    private fun login() {
        activityScope.launch {
            try {
                //authentification auprès de l'api avec le pseudo et mdp entrés
                val pseudo = textPseudo.text.toString()
                val pass = textPass.text.toString()
                Log.i("apirequest", "pseudo : $pseudo")
                Log.i("apirequest", "pass : $pass")
                val hash = DataProvider.authenticate(pseudo, pass)
                Log.i("apirequest", "hash : $hash")

                // sauvegarde le pseudo dans les préférences
                sharedPrefsEditor.putString("pseudo", pseudo)
                sharedPrefsEditor.commit()

                // stockage du hash dans les préférences
                sharedPrefsEditor.putString("hash", hash)
                sharedPrefsEditor.commit()

            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // vérifie l'accès au réseau
    fun isOnline(): Boolean {
        val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connMgr.activeNetworkInfo
        return networkInfo?.isConnected == true
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