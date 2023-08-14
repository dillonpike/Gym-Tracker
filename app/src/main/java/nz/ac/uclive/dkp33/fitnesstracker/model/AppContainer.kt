package nz.ac.uclive.dkp33.fitnesstracker.model

import android.content.Context

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val workoutRepository: WorkoutRepository
    val exerciseRepository: ExerciseRepository
}

/**
 * [AppContainer] implementation that provides instance of [WorkoutRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [WorkoutRepository]
     */
    override val workoutRepository: WorkoutRepository by lazy {
        WorkoutRepository(WorkoutDatabase.getDatabase(context).workoutDao())
    }

    override val exerciseRepository: ExerciseRepository by lazy {
        ExerciseRepository(WorkoutDatabase.getDatabase(context).exerciseDao())
    }
}
