package nz.ac.uclive.dkp33.fitnesstracker.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import nz.ac.uclive.dkp33.fitnesstracker.R
import nz.ac.uclive.dkp33.fitnesstracker.Screen

@Composable
fun HomeScreen(navController: NavController) {
    val configuration = LocalConfiguration.current
    if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
        PortraitHomeScreen(navController = navController)
    } else {
        LandscapeHomeScreen(navController = navController)
    }
}

@Composable
private fun PortraitHomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colors.surface),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.5f),
            contentAlignment = Alignment.Center
        ) {
            GymIcon()
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.5f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AddWorkoutButton(navController = navController)
            WorkoutHistoryButton(navController = navController)
        }
    }
}

@Composable
private fun LandscapeHomeScreen(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colors.surface),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.5f),
            contentAlignment = Alignment.Center
        ) {
            GymIcon()
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.5f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AddWorkoutButton(navController = navController)
            WorkoutHistoryButton(navController = navController)
        }
    }
}

@Composable
private fun GymIcon() {
    val icon = if (isSystemInDarkTheme()) {
        R.drawable.ic_launcher_foreground_dark
    } else {
        R.drawable.ic_launcher_foreground
    }
    Image(
        painter = painterResource(id = icon),
        contentDescription = stringResource(R.string.dumbbell_icon),
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun AddWorkoutButton(navController: NavController) {
    Button(
        onClick = { navController.navigate(Screen.WorkoutTracking.title) },
        modifier = Modifier.padding(8.dp)
    ) {
        Text(text = stringResource(R.string.start_workout))
    }
}

@Composable
private fun WorkoutHistoryButton(navController: NavController) {
    Button(
        onClick = { navController.navigate(Screen.WorkoutHistory.title) },
        modifier = Modifier.padding(8.dp)
    ) {
        Text(text = stringResource(R.string.workout_history))
    }
}