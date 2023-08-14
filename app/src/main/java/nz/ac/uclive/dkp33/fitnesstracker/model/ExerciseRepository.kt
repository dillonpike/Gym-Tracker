package nz.ac.uclive.dkp33.fitnesstracker.model

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class ExerciseRepository(private val exerciseDao: ExerciseDao) {
    val exercises: Flow<List<Exercise>> = exerciseDao.getExercises()

    @WorkerThread
    suspend fun insert(exercise: Exercise) {
        exerciseDao.insert(exercise)
    }
}
