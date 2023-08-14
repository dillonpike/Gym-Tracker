package nz.ac.uclive.dkp33.fitnesstracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.Date

@Entity(tableName = "workout")
@TypeConverters(Converters::class)
data class Workout(
    @PrimaryKey(autoGenerate = true) val workoutId: Long = 0,
    val date: Date
)