package com.example.todolist.data

import com.example.todolist.R
import com.example.todolist.model.ListeToDo


class Datasource {
    fun loadLists() : List<ListeToDo> {
        return listOf<ListeToDo>(
            ListeToDo("Liste 1"),
            ListeToDo("Liste 2"),
            ListeToDo("Liste 3")
        )
    }
}