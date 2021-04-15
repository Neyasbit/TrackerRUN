package com.example.tracker.repositories

import com.example.tracker.db.Run
import com.example.tracker.db.RunDAO
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val runDAO: RunDAO
) {
    val totalTimesInMillis = runDAO.getTotalTimesInMillis()
    val totalCaloriesBurned = runDAO.getTotalCaloriesBurned()
    val totalDistance = runDAO.getTotalDistance()
    val totalAverageSpeed = runDAO.getTotalAverageSpeed()

    val runsSortedByDate = runDAO.filterBy("timestamp")

    suspend fun insertRun(model: Run) = runDAO.insertRun(model)

    suspend fun deleteRun(model: Run) = runDAO.deleteRun(model)

    fun runsSortedByColumnName(columnName: String) = runDAO.filterBy(columnName)
}