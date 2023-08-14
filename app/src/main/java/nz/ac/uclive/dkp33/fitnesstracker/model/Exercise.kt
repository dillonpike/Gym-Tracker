package nz.ac.uclive.dkp33.fitnesstracker.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Exercise(
    @PrimaryKey(autoGenerate = true) val exerciseId: Long = 0,
    var workoutId: Long,
    val name: String,
    val weights: List<Float>,
    val reps: List<Int>
)