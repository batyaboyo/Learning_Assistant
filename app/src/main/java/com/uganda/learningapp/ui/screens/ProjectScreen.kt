package com.uganda.learningapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.uganda.learningapp.data.AppDatabase
import com.uganda.learningapp.data.entity.ProjectEntity

@Composable
fun ProjectScreen(database: AppDatabase) {
    val projects by database.roadmapDao().getAllProjects().collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            // Title handled if embedded differently, but okay here
        }
    ) { padding ->
        LazyColumn(contentPadding = padding, modifier = Modifier.fillMaxSize()) {
            item {
                Text("My Projects", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(16.dp))
            }
            items(projects) { project ->
                ProjectCard(project)
            }
        }
    }
}

@Composable
fun ProjectCard(project: ProjectEntity) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = project.title, style = MaterialTheme.typography.titleMedium)
            Text(text = project.phaseRef, style = MaterialTheme.typography.labelSmall)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = project.description, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
