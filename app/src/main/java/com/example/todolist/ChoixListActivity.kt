package com.example.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.adapter.ListAdapter
import com.example.todolist.data.Datasource
import com.example.todolist.model.ListeToDo
import java.util.concurrent.RecursiveAction

class ChoixListActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.choix_list_activity)

        // TODO : Récupère le pseudo dans les préférences et affiche les listes associées

        // TODO : Un clic sur une liste lance ShowListActivity

        // TODO : Nouvelle Liste

        // Liste test
        val myDataset = listOf<ListeToDo>(
                ListeToDo("Liste 1"),
                ListeToDo("Liste 2"),
                ListeToDo("Liste 3")
        )

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_list)
        recyclerView.adapter = ListAdapter(this, myDataset)

        val btnOk = findViewById<Button>(R.id.nouvelle_liste_btn_ok)
        btnOk.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        // lance ShowListActivity
        val intent = Intent(this, ShowListActivity::class.java)
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
}