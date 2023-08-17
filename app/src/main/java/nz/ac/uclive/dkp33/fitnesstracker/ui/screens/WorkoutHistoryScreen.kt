package nz.ac.uclive.dkp33.fitnesstracker

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.*
import nz.ac.uclive.dkp33.fitnesstracker.model.Exercise
import nz.ac.uclive.dkp33.fitnesstracker.model.WorkoutViewModel
import nz.ac.uclive.dkp33.fitnesstracker.model.WorkoutWithExercises
import nz.ac.uclive.dkp33.fitnesstracker.ui.AppViewModelProvider
import nz.ac.uclive.dkp33.fitnesstracker.ui.composables.BackButton
import nz.ac.uclive.dkp33.fitnesstracker.ui.composables.DeleteConfirmationDialog
import nz.ac.uclive.dkp33.fitnesstracker.ui.composables.ScreenHeading
import nz.ac.uclive.dkp33.fitnesstracker.ui.composables.ThreeDotsIconButton
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun WorkoutHistoryScreen(navController: NavController, workoutViewModel: WorkoutViewModel = viewModel(factory = AppViewModelProvider.Factory)) {
    val workoutHistory by workoutViewModel.workouts.observeAsState(listOf())
    Scaffold(
        topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                BackButton(navController)
                ScreenHeading(text = stringResource(R.string.workout_history))
            }
        },
    ) { innerPadding ->
        if (workoutHistory.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                ScreenHeading(
                    text = stringResource(R.string.no_workouts_yet),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(innerPadding)
            ) {
                items(workoutHistory) { workoutWithExercises ->
                    WorkoutHistoryItem(
                        workoutWithExercises = workoutWithExercises,
                        workoutViewModel = workoutViewModel
                    )
                }
            }
        }
    }
}

@SuppressLint("SimpleDateFormat")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WorkoutHistoryItem(workoutWithExercises: WorkoutWithExercises, workoutViewModel: WorkoutViewModel) {
    var expanded by remember { mutableStateOf(false) }
    var deleteDialogVisible by remember { mutableStateOf(false) }
    val haptic = LocalHapticFeedback.current
    val context = LocalContext.current
    val workoutText = getWorkoutText(workoutWithExercises)

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
            Row {
                val dateFormatter = SimpleDateFormat(stringResource(R.string.date_format))
                val formattedDate = dateFormatter.format(workoutWithExercises.workout.date)
                Text(
                    text = stringResource(R.string.workout_date_heading, formattedDate),
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.weight(1f))
                ThreeDotsIconButton(
                    onShareClick = { sendWorkoutIntent(context, workoutText) },
                    onDeleteClick = { deleteDialogVisible = true }
                )
                if (deleteDialogVisible) {
                    DeleteConfirmationDialog(
                        {
                            workoutViewModel.deleteWorkout(workoutWithExercises.workout)
                            deleteDialogVisible = false
                        },
                        { deleteDialogVisible = false }
                    )
                }
            }
            Text(
                fontWeight = FontWeight.Bold,
                text = stringResource(R.string.exercises_heading)
            )
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
    val bestSetIndex = getBestSetIndex(exercise)

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

@SuppressLint("SimpleDateFormat")
@Composable
private fun getWorkoutText(workoutWithExercises: WorkoutWithExercises): String {
    val dateFormatter = SimpleDateFormat(stringResource(R.string.date_format))
    val formattedDate = dateFormatter.format(workoutWithExercises.workout.date)

    return StringBuilder().apply {
        append(stringResource(R.string.workout_date_heading, formattedDate))
        append(stringResource(R.string.new_line))
        append(stringResource(R.string.exercises_heading))
        append(stringResource(R.string.new_line))

        workoutWithExercises.exercises.forEachIndexed { index, exercise ->
            val bestSetIndex = getBestSetIndex(exercise)
            append(stringResource(R.string.condensed_exercise, exercise.name, exercise.weights[bestSetIndex], exercise.reps[bestSetIndex]))
            append(stringResource(R.string.new_line))

            exercise.weights.forEachIndexed { setIndex, weight ->
                val reps = exercise.reps[setIndex]
                append(stringResource(R.string.set_information, setIndex + 1, weight, reps))
                append(stringResource(R.string.new_line))
            }
        }
    }.toString()
}

private fun getBestSetIndex(exercise: Exercise): Int {
    var maxVolume = 0.0f
    var bestSetIndex = 0

    for (i in exercise.weights.indices) {
        val volume = exercise.weights[i] * exercise.reps[i]
        if (volume > maxVolume) {
            maxVolume = volume
            bestSetIndex = i
        }
    }
    return bestSetIndex
}

@SuppressLint("QueryPermissionsNeeded")
private fun sendWorkoutIntent(context: Context, workoutText: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "image/png"
        putExtra(Intent.EXTRA_TEXT, workoutText)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(Intent.createChooser(intent, context.getString(R.string.share_workout_intent_title)))
    }
}