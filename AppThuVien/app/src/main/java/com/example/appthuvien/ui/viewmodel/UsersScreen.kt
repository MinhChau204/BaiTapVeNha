package com.example.appthuvien.ui.viewmodel

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight

@Composable
fun UsersScreen(viewModel: LibraryViewModel) {
    val usersState = viewModel.users.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Danh sách người dùng",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
                .align(Alignment.CenterHorizontally)
            )


        LazyColumn {
            items(usersState.value) { user ->
                Card(
                    modifier = Modifier
                        .padding(vertical = 6.dp)
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(text = "👤 Tên: ${user.name}")
                        Text(text = "🆔 ID: ${user.id}")

                        if (user.borrowedBooks.isNotEmpty()) {
                            Text(text = "📚 Sách đã mượn:", style = MaterialTheme.typography.labelMedium)
                            user.borrowedBooks.forEach { book ->
                                Text(
                                    text = "- ${book.title} (${book.author})",
                                    fontStyle = FontStyle.Italic
                                )
                            }
                        } else {
                            Text(text = "❌ Chưa mượn sách nào.")
                        }
                    }
                }
            }
        }
    }
}