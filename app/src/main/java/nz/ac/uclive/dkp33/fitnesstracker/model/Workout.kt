package nz.ac.uclive.dkp33.fitnesstracker.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.Date

@Entity(tableName = "workout")
@TypeConverters(DateConverter::class)
data class Workout(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo var date: Date,
    @ColumnInfo var exercises: List<Exercise>
)