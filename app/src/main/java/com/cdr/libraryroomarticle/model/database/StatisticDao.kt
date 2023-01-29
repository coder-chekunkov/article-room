package com.cdr.libraryroomarticle.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.cdr.libraryroomarticle.model.database.entities.StatisticDbEntity

@Dao
interface StatisticDao {

    @Insert(entity = StatisticDbEntity::class)
    fun insertNewStatisticData(statistic: StatisticDbEntity)

    @Query("SELECT statistic.id, result_name, difficulty_name, mistakes, points FROM statistic\n" +
            "INNER JOIN results ON statistic.result_id = results.id\n" +
            "INNER JOIN difficulty_levels ON statistic.difficult_id = difficulty_levels.id;")
    fun getAllStatisticData(): List<StatisticInfoTuple>

    @Query("DELETE FROM statistic WHERE id = :statisticId")
    fun deleteStatisticDataById(statisticId: Long)
}