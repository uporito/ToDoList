package com.example.todolist.data

import android.util.Log
import com.example.todolist.api.ServiceToDo
import com.example.todolist.model.ListeToDo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


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

    suspend fun getCurrentUserLists(pseudo: String, pass: String): String{
        //Log.i("apirequest", "call connexion")
        return service.getCurrentUserLists(pseudo, pass).hash
    }

}