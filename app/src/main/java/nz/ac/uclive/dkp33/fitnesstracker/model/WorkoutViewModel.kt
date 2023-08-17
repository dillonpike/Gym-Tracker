package nz.ac.uclive.dkp33.fitnesstracker.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

class WorkoutViewModel(private val workoutRepository: WorkoutRepository, private val exerciseRepository: ExerciseRepository) : ViewModel() {

    val workouts: LiveData<List<WorkoutWithExercises>> = workoutRepository.workouts.asLiveData()

    fun addWorkout() = viewModelScope.launch {
        val workout = Workout(date = Date())
        val workoutId = workoutRepository.insert(workout)
        _exercises.value?.forEach { exercise ->
            exercise.workoutId = workoutId
            exerciseRepository.insert(exercise)
        }
    }

    fun deleteWorkout(workout: Workout) = viewModelScope.launch {
        exerciseRepository.deleteByWorkoutId(workout.workoutId)
        workoutRepository.delete(workout)
    }

    private val _exercises = MutableLiveData<List<Exercise>>(listOf(Exercise(name = "", workoutId = 0, weights = listOf(0f), reps = listOf(0))))
    val exercises: LiveData<List<Exercise>>
        get() = _exercises

    private val _name = MutableLiveData("")
    val name: LiveData<String>
        get() = _name

    fun updateExercise(index: Int, updatedExercise: Exercise) {
        val updatedExercises = exercises.value?.toMutableList() ?: mutableListOf()
        updatedExercises[index] = updatedExercise
        _exercises.value = updatedExercises
    }

    fun addExercise() {
        val updatedExercises = exercises.value?.toMutableList() ?: mutableListOf()
        updatedExercises.add(Exercise(name = "", workoutId = 0, weights = listOf(0f), reps = listOf(0)))
        _exercises.value = updatedExercises
    }

    fun addSet(exerciseIndex: Int) {
        val exerciseList = exercises.value?.toMutableList() ?: return
        val exercise = exerciseList[exerciseIndex]
        val newWeights = exercise.weights.toMutableList()
        val newReps = exercise.reps.toMutableList()
        newWeights.add(newWeights.last())
        newReps.add(newReps.last())
        val updatedExercise = exercise.copy(weights = newWeights, reps = newReps)
        exerciseList[exerciseIndex] = updatedExercise
        _exercises.value = exerciseList
    }
    fun updateSetValue(exerciseIndex: Int, setIndex: Int, newValue: String, isWeight: Boolean) {
        val exerciseList = exercises.value?.toMutableList() ?: return
        if (exerciseList.size > exerciseIndex) {
            val exercise = exerciseList[exerciseIndex]
            val newWeights = exercise.weights.toMutableList()
            val newReps = exercise.reps.toMutableList()
            if (newWeights.isNotEmpty()) {
                if (isWeight) {
                    newWeights[setIndex] = newValue.toFloatOrNull() ?: 0f
                } else {
                    newReps[setIndex] = newValue.toIntOrNull() ?: 0
                }
                val updatedExercise = exercise.copy(weights = newWeights, reps = newReps)
                exerciseList[exerciseIndex] = updatedExercise
                _exercises.value = exerciseList
            }
        }
    }
}