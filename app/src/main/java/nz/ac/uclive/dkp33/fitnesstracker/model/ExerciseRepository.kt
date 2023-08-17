package nz.ac.uclive.dkp33.fitnesstracker.model

import androidx.annotation.WorkerThread

class ExerciseRepository(private val exerciseDao: ExerciseDao) {
    @WorkerThread
    suspend fun insert(exercise: Exercise) {
        exerciseDao.insert(exercise)
    }

    @WorkerThread
    suspend fun deleteByWorkoutId(workoutId: Long): Int {
        return exerciseDao.deleteByWorkoutId(workoutId)
    }
}
