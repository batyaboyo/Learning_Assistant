package com.uganda.learningapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.uganda.learningapp.data.entity.ModuleEntity
import com.uganda.learningapp.data.entity.TaskEntity
import com.uganda.learningapp.data.entity.WeekUnitEntity
import kotlinx.coroutines.flow.Flow

import com.uganda.learningapp.data.entity.ProjectEntity
import com.uganda.learningapp.data.entity.QuizEntity

@Dao
interface RoadmapDao {
    @Query("SELECT * FROM quizzes WHERE weekId = :weekId")
    fun getQuizzesForWeek(weekId: Int): Flow<List<QuizEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuiz(quiz: QuizEntity)

    @Query("SELECT * FROM projects ORDER BY id ASC")
    fun getAllProjects(): Flow<List<ProjectEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProject(project: ProjectEntity)

    @Query("UPDATE projects SET isCompleted = :isCompleted WHERE id = :projectId")
    suspend fun updateProjectCompletion(projectId: Int, isCompleted: Boolean)

    @Query("SELECT * FROM modules ORDER BY id ASC")
    fun getAllModules(): Flow<List<ModuleEntity>>

    @Query("SELECT * FROM weeks WHERE moduleId = :moduleId ORDER BY id ASC")
    fun getWeeksForModule(moduleId: Int): Flow<List<WeekUnitEntity>>

    @Query("SELECT * FROM tasks WHERE weekId = :weekId")
    fun getTasksForWeek(weekId: Int): Flow<List<TaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertModule(module: ModuleEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeek(week: WeekUnitEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Query("UPDATE weeks SET isCompleted = :isCompleted WHERE id = :weekId")
    suspend fun updateWeekCompletion(weekId: Int, isCompleted: Boolean)

    @Query("UPDATE tasks SET isCompleted = :isCompleted WHERE id = :taskId")
    suspend fun updateTaskCompletion(taskId: Int, isCompleted: Boolean)
}
