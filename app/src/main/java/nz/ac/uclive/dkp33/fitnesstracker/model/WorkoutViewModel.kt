package nz.ac.uclive.dkp33.fitnesstracker.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class WorkoutViewModel : ViewModel() {

    private val _workoutDate = MutableLiveData<Date>(Calendar.getInstance().time)
    val workoutDate: LiveData<Date>
        get() = _workoutDate

    private val _exercises = MutableLiveData<List<Exercise>>(listOf(Exercise("", listOf(0f to 0))))
    val exercises: LiveData<List<Exercise>>
        get() = _exercises

    private val _name = MutableLiveData("")
    val name: LiveData<String>
        get() = _name

    fun setWorkoutDate(date: Date) {
        _workoutDate.value = date
    }

    fun setExercises(newExercises: List<Exercise>) {
        _exercises.value = newExercises
    }

    fun updateExercise(index: Int, updatedExercise: Exercise) {
        val updatedExercises = exercises.value?.toMutableList() ?: mutableListOf()
        updatedExercises[index] = updatedExercise
        _exercises.value = updatedExercises
    }

    fun addExercise() {
        val updatedExercises = exercises.value?.toMutableList() ?: mutableListOf()
        updatedExercises.add(Exercise("", listOf(0f to 0)))
        _exercises.value = updatedExercises
    }

    fun addSet(exerciseIndex: Int) {
        val exerciseList = exercises.value?.toMutableList() ?: return
        val exercise = exerciseList[exerciseIndex]
        val newSets = exercise.sets.toMutableList()
        newSets.add(newSets.last())
        val updatedExercise = exercise.copy(sets = newSets)
        exerciseList[exerciseIndex] = updatedExercise
        _exercises.value = exerciseList
    }
    fun updateSetValue(exerciseIndex: Int, setIndex: Int, newValue: String, isWeight: Boolean) {
        val exerciseList = exercises.value?.toMutableList() ?: return
        if (exerciseList.size > exerciseIndex) {
            val exercise = exerciseList[exerciseIndex]
            val newSets = exercise.sets.toMutableList()
            if (newSets.isNotEmpty()) {
                if (isWeight) {
                    val newWeight = newValue.toFloatOrNull() ?: 0f
                    newSets[setIndex] = newWeight to newSets[setIndex].second
                } else {
                    val newReps = newValue.toIntOrNull() ?: 0
                    newSets[setIndex] = newSets[setIndex].first to newReps
                }
                val updatedExercise = exercise.copy(sets = newSets)
                exerciseList[exerciseIndex] = updatedExercise
                _exercises.value = exerciseList
            }
        }
    }
}