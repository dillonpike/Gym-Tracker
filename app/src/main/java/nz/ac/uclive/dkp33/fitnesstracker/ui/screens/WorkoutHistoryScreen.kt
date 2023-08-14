package nz.ac.uclive.dkp33.fitnesstracker

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.*
import nz.ac.uclive.dkp33.fitnesstracker.model.Exercise
import nz.ac.uclive.dkp33.fitnesstracker.model.WorkoutViewModel
import nz.ac.uclive.dkp33.fitnesstracker.model.WorkoutWithExercises
import nz.ac.uclive.dkp33.fitnesstracker.ui.AppViewModelProvider
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun WorkoutHistoryScreen(navController: NavController, workoutViewModel: WorkoutViewModel = viewModel(factory = AppViewModelProvider.Factory)) {
    val workoutHistory by workoutViewModel.workouts.observeAsState(listOf())
    LazyColumn {
        items(workoutHistory) { workoutWithExercises ->
            WorkoutHistoryItem(workoutWithExercises = workoutWithExercises)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WorkoutHistoryItem(workoutWithExercises: WorkoutWithExercises) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp,
        onClick = {
            expanded = !expanded
        }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            val dateFormatter = SimpleDateFormat("dd/MM/yyyy")
            val formattedDate = dateFormatter.format(workoutWithExercises.workout.date)
            Text(
                text = "Workout Date: $formattedDate",
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("Exercises:")
            Spacer(modifier = Modifier.height(4.dp))
            Column {
                workoutWithExercises.exercises.forEachIndexed { index, exercise ->
                    ExerciseItem(exercise = exercise, showSets = expanded)
                }
            }
        }
    }
}

@Composable
fun ExerciseItem(exercise: Exercise, showSets: Boolean) {
    val alpha = animateFloatAsState(if (showSets) 1f else 0f).value
    var maxVolume = 0.0f
    var bestSetIndex = 0

    for (i in exercise.weights.indices) {
        val volume = exercise.weights[i] * exercise.reps[i]
        if (volume > maxVolume) {
            maxVolume = volume
            bestSetIndex = i
        }
    }

    Text(
        text = "${exercise.name}  Best Set: ${exercise.reps[bestSetIndex]} x ${exercise.weights[bestSetIndex]} kg",
        fontWeight = FontWeight.Bold
    )
    AnimatedVisibility(
        visible = showSets,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically()
    ) {
        Column(
            modifier = Modifier.alpha(alpha)
        ) {
            exercise.weights.forEachIndexed { index, weight ->
                val reps = exercise.reps[index]
                Text(
                    text = "  Set ${index + 1}: Weight: $weight kg, Reps: $reps",
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(4.dp))
}
