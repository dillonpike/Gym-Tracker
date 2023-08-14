package nz.ac.uclive.dkp33.fitnesstracker.model

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class WorkoutRepository(private val workoutDao: WorkoutDao) {
    val workouts: Flow<List<Workout>> = workoutDao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(workout: Workout) {
        workoutDao.insert(workout)
    }
}
