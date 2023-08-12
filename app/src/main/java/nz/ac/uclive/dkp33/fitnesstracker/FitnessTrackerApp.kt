package nz.ac.uclive.dkp33.fitnesstracker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun FitnessTrackerApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Screen.Home.title) {
        composable(Screen.Home.title) {
            HomeScreen(navController)
        }
        composable(Screen.WorkoutTracking.title) {
            WorkoutTrackingScreen(navController)
        }
        composable(Screen.WorkoutHistory.title) {
            WorkoutHistoryScreen(navController)
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { navController.navigate(Screen.WorkoutTracking.title) },
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Start Workout")
        }
        Button(
            onClick = { navController.navigate(Screen.WorkoutHistory.title) },
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Workout History")
        }
    }
}

@Composable
fun WorkoutTrackingScreen(navController: NavController) {
    Text(text = "Tracking")
}

sealed class Screen(val title: String) {
    object Home : Screen("Home")
    object WorkoutTracking : Screen("Workout Tracking")
    object WorkoutHistory : Screen("Workout History")
}