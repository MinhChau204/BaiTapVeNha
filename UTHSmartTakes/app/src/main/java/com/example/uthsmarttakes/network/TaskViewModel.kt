package com.example.uthsmarttakes.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaskViewModel : ViewModel() {
    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> get() = _tasks

    fun fetchTasks() {
        RetrofitInstance.api.getTasks().enqueue(object : Callback<List<Task>> {
            override fun onResponse(call: Call<List<Task>>, response: Response<List<Task>>) {
                if (response.isSuccessful) {
                    _tasks.value = response.body() ?: emptyList()
                }
            }

            override fun onFailure(call: Call<List<Task>>, t: Throwable) {
                _tasks.value = emptyList()
            }
        })
    }
}
