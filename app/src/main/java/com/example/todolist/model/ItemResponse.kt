package com.example.todolist.model

data class ItemResponse( val version : String,
                          val success : String,
                          val status : String,
                          val items : MutableList<ItemToDo>)

//"version": 1.1,
//"success": true,
//"status": 200,
//"items": [