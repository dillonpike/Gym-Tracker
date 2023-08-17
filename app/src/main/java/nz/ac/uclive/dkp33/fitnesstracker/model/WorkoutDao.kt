package nz.ac.uclive.dkp33.fitnesstracker.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {
    @Insert
    suspend fun insert(workout: Workout): Long

    @Delete
    suspend fun delete(workout: Workout): Int

    @Transaction
    @Query("SELECT * FROM workout ORDER BY date DESC")
    fun getWorkoutsWithExercises(): Flow<List<WorkoutWithExercises>>
}
