package com.uganda.learningapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "projects")
data class ProjectEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val phaseRef: String, // "Phase 1"
    val isCompleted: Boolean = false
)
