package com.uganda.learningapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.uganda.learningapp.data.dao.RoadmapDao
import com.uganda.learningapp.data.entity.ModuleEntity
import com.uganda.learningapp.data.entity.TaskEntity
import com.uganda.learningapp.data.entity.WeekUnitEntity

import com.uganda.learningapp.data.entity.ProjectEntity
import com.uganda.learningapp.data.entity.QuizEntity

@Database(
    entities = [ModuleEntity::class, WeekUnitEntity::class, TaskEntity::class, ProjectEntity::class, QuizEntity::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun roadmapDao(): RoadmapDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "learning_app_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
