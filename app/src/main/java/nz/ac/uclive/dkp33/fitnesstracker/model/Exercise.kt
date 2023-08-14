package nz.ac.uclive.dkp33.fitnesstracker.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise")
data class Exercise(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo var name: String,
    @ColumnInfo val sets: List<Pair<Float, Int>>
)
