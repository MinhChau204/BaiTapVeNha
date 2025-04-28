//package com.example.uthsmarttakes
//
//import android.os.Bundle
//import android.util.Log
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.*
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//import com.example.uthsmarttakes.network.Task
//import com.example.uthsmarttakes.network.RetrofitInstance
//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            val navController = rememberNavController()
//
//            NavHost(navController, startDestination = "task_list") {
//                composable("task_list") { TaskListScreen(navController) }
//                composable(
//                    "task_detail/{taskId}/{taskTitle}",
//                    arguments = listOf(
//                        navArgument("taskId") { defaultValue = "" },
//                        navArgument("taskTitle") { defaultValue = "" }
//                    )
//                ) { backStackEntry ->
//                    DetailScreen(
//                        navController,
//                        backStackEntry.arguments?.getString("taskId"),
////                        backStackEntry.arguments?.getString("taskTitle")
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun TaskListScreen(navController: NavController) {
//    val taskList = remember { mutableStateListOf<Task>() }
//    var isLoading by remember { mutableStateOf(true) }
//
//    LaunchedEffect(Unit) {
//        fetchTasks(taskList) { isLoading = false }
//    }
//
//    Column(
//        modifier = Modifier.fillMaxSize().padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text("Danh sách công việc", style = MaterialTheme.typography.headlineMedium)
//        Spacer(modifier = Modifier.height(8.dp))
//
//        if (isLoading) {
//            CircularProgressIndicator()
//        } else if (taskList.isEmpty()) {
//            Text(
//                "No Tasks Yet!",
//                style = MaterialTheme.typography.bodyLarge,
//                textAlign = TextAlign.Center,
//                modifier = Modifier.fillMaxSize()
//            )
//        } else {
//            LazyColumn {
//                items(taskList) { task ->
//                    TaskItem(task) {
//                        navController.navigate("task_detail/${task.id}/${task.title}")
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
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 4.dp)
//            .clickable { onClick() },
//        shape = MaterialTheme.shapes.medium,
//        elevation = CardDefaults.cardElevation(4.dp)
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(text = "${task.id}. ${task.title}", style = MaterialTheme.typography.bodyLarge)
//        }
//    }
//}


package com.example.uthsmarttakes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.uthsmarttakes.ui.theme.UTHSmartTakesTheme
import com.example.uthsmarttakes.navigation.AppNavigation


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UTHSmartTakesTheme {
                val navController = rememberNavController()
                AppNavigation(navController)
            }
        }
    }
}
