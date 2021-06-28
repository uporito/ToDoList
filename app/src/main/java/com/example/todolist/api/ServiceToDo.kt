package com.example.todolist.api

import com.example.todolist.model.ItemResponse
import com.example.todolist.model.ListeResponse
import com.example.todolist.model.ListeToDo
import com.example.todolist.model.Login
import retrofit2.Call
import retrofit2.http.*

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

    @GET("lists")
    suspend fun getCurrentUserLists(@Header("hash") hash: String) : ListeResponse

    @GET("lists/{idList}/items")
    suspend fun getListItems(@Header("hash") hash: String,
                             @Path("idList") idList: String) : ItemResponse

    @PUT("lists/{idList}/items/{idItem}")
    suspend fun checkItem(@Header("hash") hash: String,
                          @Path("idList") idList: String,
                          @Path("idItem") idItem: String,
                          @Query("check") check : String)

    @POST("lists/{idList}/items")
    suspend fun newItem(@Header("hash") hash: String,
                        @Path("idList") idList: String,
                        @Query("label") label: String)

    @POST("lists")
    suspend fun newList(@Header("hash") hash: String,
                        @Query("label") label: String)


}