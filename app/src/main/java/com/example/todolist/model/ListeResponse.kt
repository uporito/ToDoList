package com.example.todolist.model

data class ListeResponse( val version : String,
                          val success : String,
                          val status : String,
                          val lists : List<ListeToDo>)

//{"version":1.1,"success":true,"status":200,"lists":[]}
