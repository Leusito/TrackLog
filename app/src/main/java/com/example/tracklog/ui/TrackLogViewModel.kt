package com.example.tracklog.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.tracklog.data.Competition
import com.example.tracklog.data.TrackLogDao
import com.example.tracklog.data.Training
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TrackLogViewModel(private val dao: TrackLogDao) : ViewModel() {

    val allTrainings: StateFlow<List<Training>> = dao.getAllTrainings()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allCompetitions: StateFlow<List<Competition>> = dao.getAllCompetitions()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun saveTraining(training: Training) {
        viewModelScope.launch {
            dao.insertTraining(training)
        }
    }

    fun saveCompetition(competition: Competition) {
        viewModelScope.launch {
            dao.insertCompetition(competition)
        }
    }

    fun deleteTraining(training: Training) {
        viewModelScope.launch {
            dao.deleteTraining(training)
        }
    }

    fun deleteCompetition(competition: Competition) {
        viewModelScope.launch {
            dao.deleteCompetition(competition)
        }
    }
    
    fun getTrainingsForDate(date: Long): StateFlow<List<Training>> {
         return dao.getTrainingsForDate(date)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    }
    
    fun getCompetitionsForDate(date: Long): StateFlow<List<Competition>> {
         return dao.getCompetitionsForDate(date)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    }
}
