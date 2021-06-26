package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.adapter.ListAdapter
import com.example.todolist.data.Datasource
import com.example.todolist.model.ListeToDo

const val EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE"

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnOk = findViewById<Button>(R.id.pseudo_btn_ok)
        btnOk.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        // TODO:sauvegarder le pseudo dans les préférences

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

    // Je n'arrive pas à build avec ces lignes qui sont censées permettre à un clic sur Settings
    // de mener vers SettingsActivity
    //override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
    //    // pour l'instant settings est le seul cas
    //    R.id.action_settings -> {
    //        startActivity(Intent(this, SettingsActivity::class.java))
    //        true
    //    }
    //    else -> {
    //        // exception
    //        super.onOptionsItemSelected(item)
    //    }
    //}

}