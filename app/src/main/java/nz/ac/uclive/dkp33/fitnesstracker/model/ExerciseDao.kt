package nz.ac.uclive.dkp33.fitnesstracker.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    @Insert
    suspend fun insert(exercise: Exercise): Long

    @Query("DELETE FROM Exercise WHERE workoutId = :workoutId")
    suspend fun deleteByWorkoutId(workoutId: Long): Int

    @Query("SELECT * FROM Exercise")
    fun getExercises(): Flow<List<Exercise>>
}
