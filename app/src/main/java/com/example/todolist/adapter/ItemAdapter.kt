package com.example.todolist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.model.ItemToDo
import com.example.todolist.model.ListeToDo

class ItemAdapter(
    private val context : Context,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private val dataset : MutableList<ItemToDo> = mutableListOf()

    // Références vers les views de list_view
    class ItemViewHolder(val view : View) : RecyclerView.ViewHolder(view) {
        val cb : CheckBox = view.findViewById(R.id.item_cb)
    }

    /**
     * Créé des nouvelles views
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun getItemCount() : Int = dataset.size

    /**
     * Remplace le contenu d'une view
     */
    override fun onBindViewHolder(holder: ItemAdapter.ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.cb.text = item.label
        holder.cb.isChecked = (item.checked == "1")
        holder.view.setOnClickListener {
            itemClickListener.onItemClicked(holder.cb, position)
        }
    }

    fun showData(newDataSet : List<ItemToDo>) {
        dataset.clear()
        dataset.addAll(newDataSet)
        notifyDataSetChanged()
    }

}

interface OnItemClickListener {
    fun onItemClicked(v: View, pos: Int)
}