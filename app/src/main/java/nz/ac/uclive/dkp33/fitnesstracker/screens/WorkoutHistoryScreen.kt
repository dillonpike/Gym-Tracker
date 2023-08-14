package nz.ac.uclive.dkp33.fitnesstracker

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.example.inventory.ui.AppViewModelProvider
import nz.ac.uclive.dkp33.fitnesstracker.model.Exercise
import nz.ac.uclive.dkp33.fitnesstracker.model.Workout
import nz.ac.uclive.dkp33.fitnesstracker.model.WorkoutViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun WorkoutHistoryScreen(navController: NavController, workoutViewModel: WorkoutViewModel = viewModel(factory = AppViewModelProvider.Factory)) {
    val workoutHistory = generateDummyWorkoutHistory() // Replace with actual data
//    val workoutHistory = workoutViewModel.workouts.
    LazyColumn {
        items(workoutHistory) { workout ->
            WorkoutHistoryItem(workout = workout)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WorkoutHistoryItem(workout: Workout) {
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
            val formattedDate = dateFormatter.format(workout.date)
            Text(
                text = "Workout Date: $formattedDate",
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("Exercises:")
            Spacer(modifier = Modifier.height(4.dp))
            Column {
                workout.exercises.forEachIndexed { index, exercise ->
                    ExerciseItem(exercise = exercise, showSets = expanded)
                }
            }
        }
    }
}

@Composable
fun ExerciseItem(exercise: Exercise, showSets: Boolean) {
    val alpha = animateFloatAsState(if (showSets) 1f else 0f).value
    val bestSet = exercise.sets.maxWith(compareBy({ it.first }, { it.second }))

    Text(
        text = "${exercise.name}  Best Set: ${bestSet.second} x ${bestSet.first} kg",
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
            exercise.sets.forEachIndexed { index, (weight, repetitions) ->
                Text(
                    text = "  Set ${index + 1}: Weight: $weight kg, Reps: $repetitions",
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(4.dp))
}

@Composable
fun generateDummyWorkoutHistory(): List<Workout> {
    val workoutList = mutableListOf<Workout>()

    val exercise1 = Exercise(name = "Push-up", sets = listOf(5.0f to 15, 5.5f to 12, 6.0f to 10))
    val exercise2 = Exercise(name = "Squat", sets = listOf(10.0f to 10, 11.0f to 8, 12.0f to 6))
    val exercise3 = Exercise(name = "Pull-up", sets = listOf(8.0f to 8, 9.0f to 6, 10.0f to 4))
    val exercise4 = Exercise(name = "Plank", sets = listOf(1.5f to 60, 1.75f to 50))

//    val workout1 = Workout(Date(), listOf(exercise1))
//    val workout2 = Workout(Date(), listOf(exercise1, exercise2))
//    val workout3 = Workout(Date(), listOf(exercise3, exercise4))
//    val workout4 = Workout(Date(), listOf(exercise4, exercise2))
//
//    workoutList.add(workout1)
//    workoutList.add(workout2)
//    workoutList.add(workout3)
//    workoutList.add(workout4)

    // Add more workouts...

    return workoutList
}