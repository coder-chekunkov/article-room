package com.cdr.libraryroomarticle.model.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "results")
data class ResultsDbEntity(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "result_name") val resultName: String
)