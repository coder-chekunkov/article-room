package com.cdr.libraryroomarticle.screens.statistic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cdr.libraryroomarticle.model.StatisticRepository
import com.cdr.libraryroomarticle.model.database.StatisticInfoTuple
import kotlinx.coroutines.launch

class AllStatisticViewModel(private val statisticRepository: StatisticRepository) : ViewModel() {

    private val _allStatistic = MutableLiveData<List<StatisticInfoTuple>>()
    val allStatic: LiveData<List<StatisticInfoTuple>> = _allStatistic

    init {
        getAllStatistic()
    }

    val statisticItemListener = object : StatisticItemListener{
        override fun getInfoAboutStatistic(id: Long) {
            val statisticId = _allStatistic.value?.indexOfFirst { it.id == id }
            if (statisticId == -1) return

            val statisticInfo = _allStatistic.value?.get(statisticId!!)
            println(statisticInfo)
        }

        override fun removeStatistic(id: Long) {
            viewModelScope.launch {
                statisticRepository.removeStatisticDataById(id)
                getAllStatistic()
            }
        }
    }

    private fun getAllStatistic() {
        viewModelScope.launch {
            _allStatistic.value = statisticRepository.getAllStatisticData()
        }
    }
}