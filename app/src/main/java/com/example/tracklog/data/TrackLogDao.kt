package com.example.tracklog.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackLogDao {
    // Training
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTraining(training: Training)

    @Update
    suspend fun updateTraining(training: Training)

    @Delete
    suspend fun deleteTraining(training: Training)

    @Query("SELECT * FROM training_table ORDER BY date DESC")
    fun getAllTrainings(): Flow<List<Training>>

    @Query("SELECT * FROM training_table WHERE date = :date")
    fun getTrainingsForDate(date: Long): Flow<List<Training>>

    @Query("SELECT * FROM training_table WHERE date = :date LIMIT 1")
    suspend fun getTrainingByDate(date: Long): Training?

    @Query("DELETE FROM training_table WHERE date = :date")
    suspend fun deleteTrainingByDate(date: Long)

    // Competition
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCompetition(competition: Competition)

    @Update
    suspend fun updateCompetition(competition: Competition)

    @Delete
    suspend fun deleteCompetition(competition: Competition)

    @Query("SELECT * FROM competition_table ORDER BY date DESC")
    fun getAllCompetitions(): Flow<List<Competition>>

    @Query("SELECT * FROM competition_table WHERE date = :date")
    fun getCompetitionsForDate(date: Long): Flow<List<Competition>>

    @Query("SELECT * FROM competition_table WHERE date = :date LIMIT 1")
    suspend fun getCompetitionByDate(date: Long): Competition?

    @Query("DELETE FROM competition_table WHERE date = :date")
    suspend fun deleteCompetitionByDate(date: Long)
}
