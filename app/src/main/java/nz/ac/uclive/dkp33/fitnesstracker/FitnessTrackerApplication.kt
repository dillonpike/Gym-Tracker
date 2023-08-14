package nz.ac.uclive.dkp33.fitnesstracker

import android.app.Application
import nz.ac.uclive.dkp33.fitnesstracker.model.AppContainer
import nz.ac.uclive.dkp33.fitnesstracker.model.AppDataContainer
import nz.ac.uclive.dkp33.fitnesstracker.model.WorkoutDatabase
import nz.ac.uclive.dkp33.fitnesstracker.model.WorkoutRepository

class FitnessTrackerApplication : Application() {
    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}