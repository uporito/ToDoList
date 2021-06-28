package com.example.todolist.api

import com.example.todolist.model.ListeToDo
import com.example.todolist.model.Login
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ServiceToDo {

    companion object {
        val BASE_URL = "http://tomnab.fr/todo-api/"
        //val hash = "4b9c9b522c90eecdb3d7459683627e88"
    }

//    @GET("lists?hash=4b9c9b522c90eecdb3d7459683627e88")
//    fun getCurrentUserLists() : Call<List<ListeToDo>>

//    @POST("authenticate")
//    fun authenticate() : String {}
//
//    @PUT("/users")
//    suspend fun putUsers() {}
//
//    @DELETE("/users")
//    suspend fun deleteUsers() {}

    //connexion
    // l'API renvoie un hash
    @POST("authenticate")
    suspend fun authenticate(@Query("user") user: String,
                             @Query("password") password: String) : Login

    @POST("authenticate")
    suspend fun getCurrentUserLists(@Header("hash") hash: String) : ListeResponse

}