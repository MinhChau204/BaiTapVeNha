package com.example.themeselector.ui

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.themeselector.util.AppTheme
import com.example.themeselector.util.ThemePreference
import kotlinx.coroutines.launch

@Composable
fun ThemeSettingScreen(navController: NavController, context: Context) {
    var selectedTheme by remember { mutableStateOf(AppTheme.BLUE) }

    val backgroundColor = when (selectedTheme) {
        AppTheme.BLUE -> Color(0xFFEAF1FF)
        AppTheme.PINK -> Color(0xFFFF00C3)
        AppTheme.DARK -> Color(0xFF1C1C1E)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Select Theme", fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            listOf(
                AppTheme.BLUE to Color(0xFF4D7CFE),
                AppTheme.PINK to Color(0xFFFF00C3),
                AppTheme.DARK to Color.Black
            ).forEach { (theme, color) ->
                val isSelected = selectedTheme == theme
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .padding(8.dp)
                        .background(color, shape = CircleShape)
                        .clickable { selectedTheme = theme }
                        .border(
                            width = if (isSelected) 3.dp else 0.dp,
                            color = if (isSelected) Color(0xFFFF9800) else Color.Transparent,
                            shape = CircleShape
                        )
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        val scope = rememberCoroutineScope()
        Button(onClick = {
            scope.launch {
                ThemePreference.saveTheme(context, selectedTheme)
                navController.navigate("detail") {
                    popUpTo("setting") { inclusive = true }
                }
            }
        }) {
            Text("Apply")
        }
    }
}
