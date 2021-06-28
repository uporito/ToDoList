package com.example.todolist.model

data class ItemToDo(val id : String,
                    var label : String,
                    var url : String? = null,
                    var checked : String)

//"id": "1066",
//"label": "d",
//"url": null,
//"checked": "0"