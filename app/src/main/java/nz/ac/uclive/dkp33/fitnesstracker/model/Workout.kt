package nz.ac.uclive.dkp33.fitnesstracker.model

import java.util.Date

data class Workout(
    val date: Date,
    val exercises: List<Exercise>
)
