package com.example.todolist.adapter

import android.content.ClipData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.model.ListeToDo

class ListAdapter(
        private val context : Context,
        private val dataset : List<ListeToDo>
) : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    // Références vers les views de list_view
    class ListViewHolder(private val view : View) : RecyclerView.ViewHolder(view) {
        val textView : TextView = view.findViewById(R.id.list_title)
    }

    /**
     * Créer des nouvelles views
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_view, parent, false)
        return ListViewHolder(adapterLayout)
    }

    override fun getItemCount() = dataset.size

    /**
     * Remplace le contenu d'une view
     */
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = dataset[position]
        holder.textView.text = item.listTitle
    }
}