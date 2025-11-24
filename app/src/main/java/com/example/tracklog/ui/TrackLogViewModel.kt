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
            // Enforce exclusivity: remove any competition on this date
            dao.deleteCompetitionByDate(training.date)
            
            // Check if training exists to update or insert
            val existing = dao.getTrainingByDate(training.date)
            if (existing != null) {
                dao.updateTraining(training.copy(id = existing.id))
            } else {
                dao.insertTraining(training)
            }
        }
    }

    fun saveCompetition(competition: Competition) {
        viewModelScope.launch {
            // Enforce exclusivity: remove any training on this date
            dao.deleteTrainingByDate(competition.date)

            // Check if competition exists to update or insert
            val existing = dao.getCompetitionByDate(competition.date)
            if (existing != null) {
                dao.updateCompetition(competition.copy(id = existing.id))
            } else {
                dao.insertCompetition(competition)
            }
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

    fun deleteDayContent(date: Long) {
        viewModelScope.launch {
            dao.deleteTrainingByDate(date)
            dao.deleteCompetitionByDate(date)
        }
    }
    
    suspend fun getTrainingForDate(date: Long): Training? {
        return dao.getTrainingByDate(date)
    }
    
    suspend fun getCompetitionForDate(date: Long): Competition? {
        return dao.getCompetitionByDate(date)
    }
    
    fun getTrainingsForDate(date: Long) = dao.getTrainingsForDate(date)
    
    fun getCompetitionsForDate(date: Long) = dao.getCompetitionsForDate(date)
}
