package nz.ac.uclive.dkp33.fitnesstracker.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {
    @Insert
    suspend fun insert(workout: Workout): Long

    @Query("SELECT * FROM workout")
    fun getAll(): Flow<List<Workout>>

}
