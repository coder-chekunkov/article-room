package com.cdr.libraryroomarticle.screens.main

import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cdr.libraryroomarticle.model.StatisticRepository
import com.cdr.libraryroomarticle.model.database.entities.Statistic
import kotlinx.coroutines.launch

class MainViewModel(private val statisticRepository: StatisticRepository) : ViewModel() {

    private var currentResult = RESULT_WIN
    private var currentDifficultyLevel = DIFFICULTY_EASY

    fun insertNewStatisticDataInDatabase(mistakes: Long, points: Long) {
        viewModelScope.launch {
            val newStatistic = Statistic(currentResult, currentDifficultyLevel, mistakes, points)
            statisticRepository.insertNewStatisticData(newStatistic.toStatisticDbEntity())
        }
    }

    val onDifficultyLevelSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            currentDifficultyLevel = difficultyCases[p2][VALUE_DIFFICULTY] as Long
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {}
    }

    val onResultSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            currentResult = resultCases[p2][VALUE_RESULT] as Long
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {}
    }

    companion object {
        const val TITLE_DIFFICULTY = "TITLE_DIFFICULTY"
        private const val VALUE_DIFFICULTY = "VALUE_DIFFICULTY"

        private const val DIFFICULTY_EASY: Long = 1 // Уровень сложности: Лёгкая
        private const val DIFFICULTY_MIDDLE: Long = 2 // Уровень сложности: Средняя
        private const val DIFFICULTY_HARD: Long = 3 // Уровень сложности: Сложная
        private const val DIFFICULTY_EXPERT: Long = 4 // Уровень сложности: Экспертная
        val difficultyCases = mutableListOf( // Коллекция со всеми значениями сложности для Spinner
            mapOf(TITLE_DIFFICULTY to "Сложность: Лёгкая", VALUE_DIFFICULTY to DIFFICULTY_EASY),
            mapOf(TITLE_DIFFICULTY to "Сложность: Средняя", VALUE_DIFFICULTY to DIFFICULTY_MIDDLE),
            mapOf(TITLE_DIFFICULTY to "Сложность: Сложная", VALUE_DIFFICULTY to DIFFICULTY_HARD),
            mapOf(
                TITLE_DIFFICULTY to "Сложность: Экспертная",
                VALUE_DIFFICULTY to DIFFICULTY_EXPERT
            )
        )

        const val TITLE_RESULT = "TITLE_RESULT"
        private const val VALUE_RESULT = "VALUE_RESULT"

        private const val RESULT_WIN: Long = 1 // Результат: Победа
        private const val RESULT_LOST: Long = 2 // Результат: Поражение
        val resultCases = mutableListOf( // Коллекция со всеми значениями результата для Spinner
            mapOf(TITLE_RESULT to "Результат: Победа", VALUE_RESULT to RESULT_WIN),
            mapOf(TITLE_RESULT to "Результат: Поражение", VALUE_RESULT to RESULT_LOST)
        )
    }
}