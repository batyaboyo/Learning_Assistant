package com.uganda.learningapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.uganda.learningapp.data.AppDatabase
import com.uganda.learningapp.data.entity.QuizEntity
import androidx.compose.runtime.collectAsState

@Composable
fun QuizScreen(database: AppDatabase, weekId: Int, onBack: () -> Unit) {
    val quizzes by database.roadmapDao().getQuizzesForWeek(weekId).collectAsState(initial = emptyList())
    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var score by remember { mutableIntStateOf(0) }
    var showResult by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            // Optional: Title
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp)) {
            Button(onClick = onBack) { Text("Exit Quiz") }
            Spacer(modifier = Modifier.height(16.dp))

            if (quizzes.isEmpty()) {
                Text("No quizzes for this week yet.")
            } else if (showResult) {
                Text("Quiz Completed!", style = MaterialTheme.typography.headlineMedium)
                Text("Score: $score / ${quizzes.size}")
                Button(onClick = { 
                    currentQuestionIndex = 0
                    score = 0
                    showResult = false
                }) { Text("Retry") }
            } else {
                val question = quizzes.getOrNull(currentQuestionIndex)
                if (question != null) {
                    QuestionCard(question, onAnswer = { selectedIndex ->
                        if (selectedIndex == question.correctAnswerIndex) {
                            score++
                        }
                        if (currentQuestionIndex < quizzes.size - 1) {
                            currentQuestionIndex++
                        } else {
                            showResult = true
                        }
                    })
                    Text("Question ${currentQuestionIndex + 1} of ${quizzes.size}", modifier = Modifier.padding(top = 8.dp))
                }
            }
        }
    }
}

@Composable
fun QuestionCard(quiz: QuizEntity, onAnswer: (Int) -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = quiz.question, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { onAnswer(0) }, modifier = Modifier.fillMaxWidth()) { Text(quiz.optionA) }
            Button(onClick = { onAnswer(1) }, modifier = Modifier.fillMaxWidth()) { Text(quiz.optionB) }
            Button(onClick = { onAnswer(2) }, modifier = Modifier.fillMaxWidth()) { Text(quiz.optionC) }
            Button(onClick = { onAnswer(3) }, modifier = Modifier.fillMaxWidth()) { Text(quiz.optionD) }
        }
    }
}
