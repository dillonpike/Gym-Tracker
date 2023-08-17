package nz.ac.uclive.dkp33.fitnesstracker.model

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class WorkoutRepository(private val workoutDao: WorkoutDao) {
    val workouts: Flow<List<WorkoutWithExercises>> = workoutDao.getWorkoutsWithExercises()

    @WorkerThread
    suspend fun insert(workout: Workout): Long {
        return workoutDao.insert(workout)
    }

    @WorkerThread
    suspend fun delete(workout: Workout): Int {
        return workoutDao.delete(workout)
    }
}
