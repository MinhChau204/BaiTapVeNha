package com.example.uthsmarttakes.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.uthsmarttakes.ui.screens.TaskListScreen
import com.example.uthsmarttakes.ui.screens.DetailScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "task_list") {
        composable("task_list") {
            TaskListScreen(navController)
        }
        composable(
            "task_detail/{taskId}/{taskTitle}",
            arguments = listOf(
                navArgument("taskId") { type = NavType.StringType; defaultValue = "N/A" },
                navArgument("taskTitle") { type = NavType.StringType; defaultValue = "No Title" }
            )
        ) { backStackEntry ->
            DetailScreen(
                navController = navController,
                taskId = backStackEntry.arguments?.getString("taskId") ?: "N/A",
                taskTitle = backStackEntry.arguments?.getString("taskTitle") ?: "No Title"
            )
        }
    }
}
