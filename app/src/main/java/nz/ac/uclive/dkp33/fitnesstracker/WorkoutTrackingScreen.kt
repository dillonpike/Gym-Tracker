package nz.ac.uclive.dkp33.fitnesstracker

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import nz.ac.uclive.dkp33.fitnesstracker.model.Exercise
import nz.ac.uclive.dkp33.fitnesstracker.model.Workout
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun WorkoutTrackingScreen(navController: NavController) {
    var exercises by rememberSaveable { mutableStateOf(mutableListOf<Exercise>()) }
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
                Row {
                    Text(text = "Name: ")
                    OutlinedTextField(value = "",
                        onValueChange = {},
//                        modifier = Modifier.height(20.dp)
                    )
                }
            }
        }
        Button(
            onClick = {
                exercises = exercises.toMutableList().apply {
                    add(Exercise("", listOf()))
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Add Exercise")
        }
    }
}

//@Composable
//fun exerciseField()