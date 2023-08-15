package nz.ac.uclive.dkp33.fitnesstracker

import android.annotation.SuppressLint
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
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
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

@SuppressLint("SimpleDateFormat")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WorkoutHistoryItem(workoutWithExercises: WorkoutWithExercises) {
    var expanded by remember { mutableStateOf(false) }
    val haptic = LocalHapticFeedback.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp,
        onClick = {
            expanded = !expanded
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
        }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            val dateFormatter = SimpleDateFormat(stringResource(R.string.date_format))
            val formattedDate = dateFormatter.format(workoutWithExercises.workout.date)
            Text(
                text = stringResource(R.string.workout_date_heading, formattedDate),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(stringResource(R.string.exercises_heading))
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
        text = stringResource(R.string.condensed_exercise, exercise.name, exercise.weights[bestSetIndex], exercise.reps[bestSetIndex]),
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
                    text = stringResource(R.string.set_information, index + 1, weight, reps),
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(4.dp))
}
