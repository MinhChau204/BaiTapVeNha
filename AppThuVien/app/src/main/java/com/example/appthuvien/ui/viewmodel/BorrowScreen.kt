package com.example.appthuvien.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appthuvien.ui.viewmodel.LibraryViewModel

@Composable
fun BorrowScreen(viewModel: LibraryViewModel) {
    var userName by remember { mutableStateOf("") }
    var bookTitle by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        androidx.compose.material3.Text(
            text = "Mượn/Trả sách",
            style = androidx.compose.material3.MaterialTheme.typography.headlineSmall,
            color = androidx.compose.material3.MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
                .align(Alignment.CenterHorizontally)
        )

        OutlinedTextField(
            value = userName,
            onValueChange = { userName = it },
            label = { Text("Tên người mượn") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = bookTitle,
            onValueChange = { bookTitle = it },
            label = { Text("Tên sách") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    viewModel.borrowBook(userName, bookTitle)
                    userName = ""
                    bookTitle = ""
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Mượn sách")
            }

            Button(
                onClick = {
                    viewModel.returnBook(userName, bookTitle)
                    userName = ""
                    bookTitle = ""
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Trả sách")
            }
        }

        Text(
            text = viewModel.message.collectAsState().value,
            color = MaterialTheme.colors.error
        )
    }
}