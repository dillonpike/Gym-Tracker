package nz.ac.uclive.dkp33.fitnesstracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import nz.ac.uclive.dkp33.fitnesstracker.model.WorkoutDatabase
import nz.ac.uclive.dkp33.fitnesstracker.model.WorkoutRepository
import nz.ac.uclive.dkp33.fitnesstracker.ui.theme.FitnessTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FitnessTrackerTheme {
                FitnessTrackerApp()
            }
        }
    }
}