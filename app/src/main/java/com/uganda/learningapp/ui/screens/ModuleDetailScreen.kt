package com.uganda.learningapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.uganda.learningapp.data.AppDatabase
import com.uganda.learningapp.data.entity.WeekUnitEntity
import com.uganda.learningapp.data.entity.TaskEntity
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.launch

@Composable
fun ModuleDetailScreen(database: AppDatabase, moduleId: Int, onBack: () -> Unit, onTakeQuiz: (Int) -> Unit) {
    // In real app, use ViewModel + Repository
    val weeks by database.roadmapDao().getWeeksForModule(moduleId).collectAsState(initial = emptyList())

    // Simple hack to get module title? Or just pass it.
    // For now showing list of weeks.
    
    Scaffold { padding ->
        LazyColumn(contentPadding = padding, modifier = Modifier.fillMaxSize().padding(16.dp)) {
            item {
                Button(onClick = onBack) { Text("Back") }
                Spacer(modifier = Modifier.height(16.dp))
                Text("Module $moduleId Content", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(16.dp))
            }
            items(weeks) { week ->
                WeekCard(week, database, onTakeQuiz)
            }
        }
    }
}

@Composable
fun WeekCard(week: WeekUnitEntity, database: AppDatabase, onTakeQuiz: (Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val tasks by database.roadmapDao().getTasksForWeek(week.id).collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()

    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        onClick = { expanded = !expanded }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = week.weekRangeLabel + ": " + week.title, style = MaterialTheme.typography.titleMedium)
            Text(text = week.description, style = MaterialTheme.typography.bodySmall)
            
            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Divider()
                Spacer(modifier = Modifier.height(8.dp))
                Text("Tasks:", style = MaterialTheme.typography.labelLarge)
                tasks.forEach { task ->
                    TaskRow(task, onCheckedChange = { isChecked ->
                        scope.launch {
                            database.roadmapDao().updateTaskCompletion(task.id, isCompleted = isChecked)
                        }
                    })
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { onTakeQuiz(week.id) }) {
                    Text("Take Quiz")
                }
            }
        }
    }
}

@Composable
fun TaskRow(task: TaskEntity, onCheckedChange: (Boolean) -> Unit) {
    Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
        Checkbox(checked = task.isCompleted, onCheckedChange = onCheckedChange)
        Text(text = task.description, modifier = Modifier.padding(start = 8.dp))
    }
}
