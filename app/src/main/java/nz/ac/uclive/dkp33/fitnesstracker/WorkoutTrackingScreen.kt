package nz.ac.uclive.dkp33.fitnesstracker

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.*
import nz.ac.uclive.dkp33.fitnesstracker.model.Exercise
import nz.ac.uclive.dkp33.fitnesstracker.model.Workout
import nz.ac.uclive.dkp33.fitnesstracker.ui.theme.FitnessTrackerTheme
import java.text.SimpleDateFormat
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
}

@Composable
fun WorkoutTrackingScreen(navController: NavController, workoutViewModel: WorkoutViewModel = viewModel()) {
    val exercises by workoutViewModel.exercises.observeAsState(listOf())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Add Workout",
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        LazyColumn {
            itemsIndexed(exercises) { index, exercise ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    elevation = 4.dp
                ) {
                    Column {
                        CustomTextField(
                            value = exercise.name,
                            onValueChange = {
                                workoutViewModel.updateExercise(index, exercise.copy(name = it))
                            },
                            fontSize = 16.sp,
                            placeholderText = "Name"
                        )
                        SetsTitles()
                        exercise.sets.forEachIndexed { index, set ->
                            Row (
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Box(
                                    modifier = Modifier.weight(1f),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = "${index+1}")
                                }
                                Box(
                                    modifier = Modifier.weight(1f),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CustomTextField(
                                        value = "${set.first}",
                                        onValueChange = { newValue: String ->
                                            val newSets = exercise.sets.toMutableList()
                                            if (newSets.isNotEmpty()) {
                                                val newFirstValue = newValue.toFloatOrNull() ?: 0f
                                                newSets[0] = newFirstValue to newSets[0].second
                                                val updatedExercise = exercise.copy(sets = newSets)
                                                workoutViewModel.updateExercise(index, updatedExercise)
                                            }
                                        },
                                        fontSize = 16.sp,
                                        placeholderText = ""
                                    )
                                }
                                Box(
                                    modifier = Modifier.weight(1f),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = "${set.second}")
                                }
                            }
                        }
                    }
                }
            }
        }
        Button(
            onClick = {
                workoutViewModel.addExercise()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Add Exercise")
        }
    }
}

@Composable
private fun SetsTitles() {
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Set")
        }
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Weight (kg)")
        }
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Reps")
        }
    }
}

@Composable
private fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    placeholderText: String = "Placeholder",
    fontSize: TextUnit = MaterialTheme.typography.body2.fontSize
) {
    BasicTextField(
        modifier = modifier
        .background(
            MaterialTheme.colors.surface,
            MaterialTheme.shapes.small,
        ),
//        .fillMaxWidth(),
        value = value,
        onValueChange = {
            Log.v("info", it)
            onValueChange(it) },
        singleLine = true,
        cursorBrush = SolidColor(MaterialTheme.colors.primary),
        textStyle = LocalTextStyle.current.copy(
            color = MaterialTheme.colors.onSurface,
            fontSize = fontSize
        ),
        decorationBox = { innerTextField ->
            Row(
                modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (leadingIcon != null) leadingIcon()
                Box(Modifier.weight(1f)) {
                    if (value.isEmpty()) Text(
                        placeholderText,
                        style = LocalTextStyle.current.copy(
                            color = MaterialTheme.colors.onSurface.copy(alpha = 0.3f),
                            fontSize = fontSize
                        )
                    )
                    innerTextField()
                }
                if (trailingIcon != null) trailingIcon()
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FitnessTrackerTheme {
        val navController = rememberNavController()

        WorkoutTrackingScreen(navController)
    }
}