package com.example.themeselector.ui

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.themeselector.util.AppTheme
import com.example.themeselector.util.ThemePreference
import kotlinx.coroutines.launch

@Composable
fun ThemeDetailScreen(context: Context) {
    var theme by remember { mutableStateOf(AppTheme.BLUE) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        theme = ThemePreference.getTheme(context)
    }

    val backgroundColor = when (theme) {
        AppTheme.BLUE -> Color(0xFFEAF1FF)
        AppTheme.PINK -> Color(0xFFFF00C3)
        AppTheme.DARK -> Color(0xFF1C1C1E)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = theme.name.lowercase().replaceFirstChar { it.uppercase() },
                color = Color.White
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = {
                // optional back
            }) {
                Text("Back")
            }
        }
    }
}
