package com.example.uthsmarttakes.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController, taskId: String?, taskTitle: String?) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Detail", fontSize = 20.sp, fontWeight = FontWeight.Bold) })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = taskTitle ?: "Unknown Task",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text("Finish the UI, integrate API, and write documentation", fontSize = 16.sp)

            Spacer(modifier = Modifier.height(16.dp))

            Text("Subtasks", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            repeat(3) {
                SubtaskItem()
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Attachments", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            repeat(2) {
                AttachmentItem()
            }

            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Back")
            }
        }
    }
}

@Composable
fun SubtaskItem() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.LightGray)
    ) {
        Text(
            text = "This task is related to Fitness. It needs to be completed",
            modifier = Modifier.padding(12.dp),
            fontSize = 14.sp
        )
    }
}

@Composable
fun AttachmentItem() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Text(
            text = "document_1.0.pdf",
            modifier = Modifier.padding(12.dp),
            fontSize = 14.sp,
            color = Color.Blue
        )
    }
}
