package nz.ac.uclive.dkp33.fitnesstracker.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.inventory.ui.AppViewModelProvider
import nz.ac.uclive.dkp33.fitnesstracker.FitnessTrackerApplication
import nz.ac.uclive.dkp33.fitnesstracker.R
import nz.ac.uclive.dkp33.fitnesstracker.model.Exercise
import nz.ac.uclive.dkp33.fitnesstracker.model.WorkoutViewModel
import nz.ac.uclive.dkp33.fitnesstracker.model.WorkoutViewModelFactory
import nz.ac.uclive.dkp33.fitnesstracker.ui.theme.FitnessTrackerTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun WorkoutTrackingScreen(navController: NavController, workoutViewModel: WorkoutViewModel = viewModel(factory = AppViewModelProvider.Factory)) {
    val exercises by workoutViewModel.exercises.observeAsState(listOf())

    Scaffold(
        bottomBar = {
            AddExerciseButton(workoutViewModel)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row {
                AddWorkoutHeading()
                Spacer(Modifier.weight(1f))
                Button(
                    onClick = {},
                    modifier = Modifier.padding(bottom = 16.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = colors.secondary)
                ) {
                    Text(text = "Save")
                }
            }
            LazyColumn {
                itemsIndexed(exercises) { exerciseIndex, exercise ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        elevation = 4.dp
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row {
                                NameTextField(
                                    value = exercise.name,
                                    onValueChange = {
                                        workoutViewModel.updateExercise(
                                            exerciseIndex,
                                            exercise.copy(name = it)
                                        )
                                    }
                                )
                            }
                            SetTitles()
                            SetRows(workoutViewModel, exercise, exerciseIndex)
                            AddSetButton(workoutViewModel, exerciseIndex)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AddWorkoutHeading() {
    Text(
        text = "Add Workout",
        style = MaterialTheme.typography.h5,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 16.dp)
    )
}

@Composable
private fun AddExerciseButton(workoutViewModel: WorkoutViewModel) {
    Button(
        onClick = {
            workoutViewModel.addExercise()
        },
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp, horizontal=16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(id = R.string.add_set_button_description)
        )
        Text(
            text = stringResource(id = R.string.add_exercise_button),
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}

@Composable
private fun AddSetButton(workoutViewModel: WorkoutViewModel, exerciseIndex: Int) {
    Button(
        onClick = { workoutViewModel.addSet(exerciseIndex) },
        modifier = Modifier
            .padding(4.dp)
            .height(35.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(id = R.string.add_set_button_description)
        )
        Text(
            text = stringResource(id = R.string.add_set_button_description),
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}

@Composable
private fun NameTextField(value : String, onValueChange: (String) -> Unit) {
    CustomTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        fontSize = 16.sp,
        placeholderText = "Name"
    )
}

@Composable
private fun SetTextField(value: String, onValueChange: (String) -> Unit) {
    CustomTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        fontSize = 16.sp,
        placeholderText = "",
        textAlign = TextAlign.Center,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
    )
}

@Composable
private fun SetTitles() {
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
private fun SetRows(workoutViewModel: WorkoutViewModel, exercise: Exercise, exerciseIndex: Int) {
    exercise.sets.forEachIndexed { setIndex, set ->
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "${setIndex+1}")
            }
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                SetTextField(
                    value = "${set.first}",
                    onValueChange = {
                        workoutViewModel.updateSetValue(exerciseIndex, setIndex, it, true)
                    }
                )
            }
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                SetTextField(
                    value = "${set.second}",
                    onValueChange = {
                        workoutViewModel.updateSetValue(exerciseIndex, setIndex, it, false)
                    }
                )
            }
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
    fontSize: TextUnit = MaterialTheme.typography.body2.fontSize,
    textAlign: TextAlign = TextAlign.Left,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    BasicTextField(
        modifier = modifier
        .background(
            MaterialTheme.colors.surface,
            MaterialTheme.shapes.small,
        ),
        value = value,
        onValueChange = { onValueChange(it) },
        keyboardOptions = keyboardOptions,
        singleLine = true,
        cursorBrush = SolidColor(MaterialTheme.colors.primary),
        textStyle = LocalTextStyle.current.copy(
            color = MaterialTheme.colors.onSurface,
            fontSize = fontSize,
            textAlign = textAlign
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