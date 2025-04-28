package com.example.themeselector.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNav(navController: NavHostController, context: Context) {
    NavHost(navController = navController, startDestination = "setting") {
        composable("setting") {
            ThemeSettingScreen(navController, context)
        }
        composable("detail") {
            ThemeDetailScreen(context)
        }
    }
}
