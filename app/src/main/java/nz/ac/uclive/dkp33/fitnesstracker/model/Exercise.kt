package nz.ac.uclive.dkp33.fitnesstracker.model

data class Exercise(
    val name: String,
    val sets: List<Pair<Float, Int>>
)
