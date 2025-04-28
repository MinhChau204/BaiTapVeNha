package com.example.uthsmarttakes.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.uthsmarttakes.network.Task
import com.example.uthsmarttakes.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.compose.ui.graphics.Color
import com.example.uthsmarttakes.R


//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun TaskListScreen(navController: NavController) {
//    val taskList = remember { mutableStateListOf<Task>() }
//    var isLoading by remember { mutableStateOf(true) }
//
//    LaunchedEffect(Unit) {
//        fetchTasks(taskList) { isLoading = false }
//    }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(title = { Text("List") })
//        }
//    ) { padding ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(padding)
//                .padding(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            if (isLoading) {
//                CircularProgressIndicator()
//            } else if (taskList.isEmpty()) {
//                Column(
//                    modifier = Modifier.fillMaxSize(),
//                    verticalArrangement = Arrangement.Center,
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Image(painter = painterResource(id = R.drawable.ic_empty), contentDescription = "Empty Icon")
//                    Spacer(modifier = Modifier.height(8.dp))
//                    Text(
//                        "No Tasks Yet!",
//                        style = MaterialTheme.typography.bodyLarge,
//                        textAlign = TextAlign.Center
//                    )
//                }
//            } else {
//                LazyColumn {
//                    items(taskList) { task ->
//                        TaskItem(task) {
//                            navController.navigate("task_detail/${task.id}/${task.title}")
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
//private fun fetchTasks(taskList: MutableList<Task>, onComplete: () -> Unit) {
//    RetrofitInstance.api.getTasks().enqueue(object : Callback<List<Task>> {
//        override fun onResponse(call: Call<List<Task>>, response: Response<List<Task>>) {
//            if (response.isSuccessful) {
//                response.body()?.let {
//                    taskList.addAll(it)
//                }
//            } else {
//                Log.e("API_ERROR", "Lỗi tải dữ liệu: ${response.errorBody()?.string()}")
//            }
//            onComplete()
//        }
//
//        override fun onFailure(call: Call<List<Task>>, t: Throwable) {
//            Log.e("API_ERROR", "Lỗi tải dữ liệu: ${t.message}")
//            onComplete()
//        }
//    })
//}
//
//@Composable
//fun TaskItem(task: Task, onClick: () -> Unit) {
//    val colors = listOf(Color(0xFFFFCDD2), Color(0xFFC8E6C9), Color(0xFFBBDEFB))
//    val backgroundColor = colors[task.id.hashCode() % colors.size]
//
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 4.dp)
//            .clickable { onClick() },
//        shape = MaterialTheme.shapes.medium,
//        colors = CardDefaults.cardColors(containerColor = backgroundColor),
//        elevation = CardDefaults.cardElevation(4.dp)
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(text = task.title, style = MaterialTheme.typography.bodyLarge)
//            Spacer(modifier = Modifier.height(4.dp))
//            Text(text = "${task.id}", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
//        }
//    }
//}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(navController: NavController) {
    val taskList = remember { mutableStateListOf<Task>() }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        fetchTasks(taskList) { isLoading = false }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("List") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else if (taskList.isEmpty()) {
                EmptyView()
            } else {
                LazyColumn {
                    items(taskList) { task ->
                        TaskItem(task) {
                            navController.navigate("task_detail/${task.id}/${task.title}")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.ic_empty), contentDescription = "Empty Icon")
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "No Tasks Yet!",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            "Stay productive - add something to do",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )
    }
}

@Composable
fun TaskItem(task: Task, onClick: () -> Unit) {
    val colors = listOf(Color(0xFFFFCDD2), Color(0xFFC8E6C9), Color(0xFFBBDEFB))
    val backgroundColor = colors[task.id.hashCode() % colors.size]

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = task.title, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Finish the UI, integrate API, and write documentation",
                style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        }
    }
}

