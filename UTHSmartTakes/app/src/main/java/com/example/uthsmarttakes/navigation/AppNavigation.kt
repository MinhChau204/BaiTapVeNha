package com.example.uthsmarttakes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.uthsmarttakes.ui.screens.TaskListScreen
import com.example.uthsmarttakes.ui.screens.DetailScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "task_list") {
        composable("task_list") { TaskListScreen(navController) }

        composable(
            "task_detail/{taskId}/{taskTitle}",
            arguments = listOf(
                navArgument("taskId") { type = NavType.StringType; defaultValue = "" },
                navArgument("taskTitle") { type = NavType.StringType; defaultValue = "" }
            )
        ) { backStackEntry ->
            DetailScreen(
                navController,
                backStackEntry.arguments?.getString("taskId") ?: "",
                backStackEntry.arguments?.getString("taskTitle") ?: ""
            )
        }
    }
}