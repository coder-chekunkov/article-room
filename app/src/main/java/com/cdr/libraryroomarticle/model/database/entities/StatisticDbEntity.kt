package com.cdr.libraryroomarticle.model.database.entities

import androidx.room.*

@Entity(
    tableName = "statistic",
    indices = [Index("id")],
    foreignKeys = [
        ForeignKey(
            entity = ResultsDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["result_id"]
        ),
        ForeignKey(
            entity = DifficultyLevelsDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["difficult_id"]
        )
    ]
)
data class StatisticDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "result_id") val resultId: Long,
    @ColumnInfo(name = "difficult_id") val difficultId: Long,
    val mistakes: Long,
    val points: Long
)