package nz.ac.uclive.dkp33.fitnesstracker

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import nz.ac.uclive.dkp33.fitnesstracker.ui.screens.HomeScreen
import nz.ac.uclive.dkp33.fitnesstracker.ui.screens.WorkoutTrackingScreen

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

sealed class Screen(val title: String) {
    object Home : Screen("Home")
    object WorkoutTracking : Screen("Workout Tracking")
    object WorkoutHistory : Screen("Workout History")
}