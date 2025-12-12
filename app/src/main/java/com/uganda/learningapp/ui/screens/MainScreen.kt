package com.uganda.learningapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.uganda.learningapp.data.AppDatabase
import com.uganda.learningapp.data.entity.ModuleEntity
import androidx.compose.runtime.collectAsState

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.DateRange
import com.uganda.learningapp.ui.screens.ProjectScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(database: AppDatabase, onModuleClick: (Int) -> Unit) {
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("CyberSec Roadmap (Uganda)") })
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Roadmap") },
                    label = { Text("Roadmap") }
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.Default.DateRange, contentDescription = "Projects") },
                    label = { Text("Projects") }
                )
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            if (selectedTab == 0) {
                RoadmapList(database, onModuleClick)
            } else {
                ProjectScreen(database)
            }
        }
    }
}

@Composable
fun RoadmapList(database: AppDatabase, onModuleClick: (Int) -> Unit) {
    val modules by database.roadmapDao().getAllModules().collectAsState(initial = emptyList())
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(modules) { module ->
            ModuleCard(module, onClick = { onModuleClick(module.id) })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModuleCard(module: ModuleEntity, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = module.title, style = MaterialTheme.typography.headlineSmall)
            Text(text = module.weekRange, style = MaterialTheme.typography.labelMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = module.description, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
