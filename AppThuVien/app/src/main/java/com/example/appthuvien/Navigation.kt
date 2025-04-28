package com.example.appthuvien

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.appthuvien.ui.viewmodel.BooksScreen
import com.example.appthuvien.ui.screen.BorrowScreen
import com.example.appthuvien.ui.viewmodel.LibraryViewModel
import com.example.appthuvien.ui.viewmodel.UsersScreen

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Books : Screen("books", "Sách", Icons.Default.Book)
    object Users : Screen("users", "Người dùng", Icons.Default.Group)
    object Borrow : Screen("borrow", "Mượn/Trả", Icons.Default.ShoppingCart)
}

@Composable
fun Navigation(navController: NavHostController, viewModel: LibraryViewModel, modifier: Modifier = Modifier) {
    val items = listOf(
        Screen.Books,
        Screen.Users,
        Screen.Borrow
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Books.route,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(Screen.Books.route) {
                BooksScreen(viewModel = viewModel)
            }
            composable(Screen.Users.route) {
                UsersScreen(viewModel = viewModel)
            }
            composable(Screen.Borrow.route) {
                BorrowScreen(viewModel = viewModel)
            }
        }
    }
}