package nz.ac.uclive.dkp33.fitnesstracker.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import nz.ac.uclive.dkp33.fitnesstracker.FitnessTrackerApplication
import nz.ac.uclive.dkp33.fitnesstracker.model.WorkoutViewModel

/**
 * Provides Factory to create instance of ViewModel for the entire app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for WorkoutViewModel
        initializer {
            WorkoutViewModel(
                fitnessTrackerApplication().container.workoutRepository,
                fitnessTrackerApplication().container.exerciseRepository
            )
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [FitnessTrackerApplication].
 */
fun CreationExtras.fitnessTrackerApplication(): FitnessTrackerApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as FitnessTrackerApplication)
