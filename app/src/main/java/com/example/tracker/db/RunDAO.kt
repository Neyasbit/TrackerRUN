package com.example.tracker.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RunDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRun(run: Run)

    @Delete
    suspend fun deleteRun(run: Run)

    @Query("""
        SELECT * FROM running_table
        ORDER BY
        CASE WHEN :column = 'timestamp'  THEN timestamp END DESC,
        CASE WHEN :column = 'timemili' THEN timeInMillis END DESC,
        CASE WHEN :column = 'calories' THEN caloriesBurned END DESC,
        CASE WHEN :column = 'speed'  THEN averageSpeedInKMH END DESC,
        CASE WHEN :column = 'distance' THEN distanceInMeters END DESC
    """)
    fun filterBy(column : String) : LiveData<List<Run>>

    @Query("SELECT SUM(timeInMillis) FROM running_table")
    fun getTotalTimesInMillis() : LiveData<Long>

    @Query("SELECT SUM(caloriesBurned) FROM running_table")
    fun getTotalCaloriesBurned() : LiveData<Int>

    @Query("SELECT SUM(distanceInMeters) FROM running_table")
    fun getTotalDistance() : LiveData<Int>

    @Query("SELECT AVG(averageSpeedInKMH) FROM running_table")
    fun getTotalAverageSpeed() : LiveData<Float>
}