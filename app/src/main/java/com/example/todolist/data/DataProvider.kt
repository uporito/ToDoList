package com.example.todolist.data

import android.util.Log
import com.example.todolist.api.ServiceToDo
import com.example.todolist.model.ItemResponse
import com.example.todolist.model.ItemToDo
import com.example.todolist.model.ListeToDo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.*


object DataProvider{

    val service = Retrofit.Builder()
        .baseUrl(ServiceToDo.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create<ServiceToDo>()

    suspend fun authenticate(pseudo: String, pass: String): String{
        //Log.i("apirequest", "call connexion")
        return service.authenticate(pseudo, pass).hash
    }

    suspend fun getCurrentUserLists(hash: String): List<ListeToDo>{
        Log.i("apirequest", "récupération listes")
        return service.getCurrentUserLists(hash).lists
    }

    suspend fun getListItems(hash: String, idList : String): List<ItemToDo>{
        Log.i("apirequest", "récupération items liste $idList")
        return service.getListItems(hash, idList).items
    }

    suspend fun checkItem(hash: String, idList : String, idItem : String, checked : String) {
        Log.i("apirequest", "changement état liste $idList, item $idItem à $checked")
        return service.checkItem(hash, idList, idItem, checked)
    }

    suspend fun newItem(hash: String, idList : String, label : String) {
        Log.i("apirequest", "ajout item $label à liste $idList")
        return service.newItem(hash, idList, label)
    }

    suspend fun newList(hash: String, label : String) {
        Log.i("apirequest", "ajout liste $label")
        return service.newList(hash, label)
    }

}