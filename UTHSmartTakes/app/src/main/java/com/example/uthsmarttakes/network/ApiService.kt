package com.example.uthsmarttakes.network

import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("tasks")
    fun getTasks(): Call<List<Task>>

    @GET("task/{id}")
    fun getTask(@Path("id") id: Int): Call<Task>

    @DELETE("task/{id}")
    fun deleteTask(@Path("id") id: Int): Call<Void>
}

