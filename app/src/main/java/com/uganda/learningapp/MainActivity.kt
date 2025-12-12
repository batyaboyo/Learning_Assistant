package com.uganda.learningapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.uganda.learningapp.data.AppDatabase
import com.uganda.learningapp.data.DataPopulator
import com.uganda.learningapp.ui.AppNavigation
import com.uganda.learningapp.ui.theme.LearningAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val database = AppDatabase.getDatabase(this)
        
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val modules = database.roadmapDao().getAllModules().first()
                if (modules.isEmpty()) {
                     DataPopulator.populate(database.roadmapDao())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        setContent {
            LearningAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(database)
                }
            }
        }
    }
}
