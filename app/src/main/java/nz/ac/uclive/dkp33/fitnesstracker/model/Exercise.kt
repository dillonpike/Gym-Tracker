package nz.ac.uclive.dkp33.fitnesstracker.model

data class Exercise(
    var name: String,
    val sets: List<Pair<Float, Int>>
)
