package com.cdr.libraryroomarticle.model.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "difficulty_levels")
data class DifficultyLevelsDbEntity(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "difficulty_name") val difficultyName: String
)