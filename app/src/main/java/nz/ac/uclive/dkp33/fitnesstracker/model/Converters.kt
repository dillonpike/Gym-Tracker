package nz.ac.uclive.dkp33.fitnesstracker.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

object Converters {
    @TypeConverter
    fun toDate(dateLong: Long?): Date? {
        return dateLong?.let { Date(it) }
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromFloatList(value: List<Float>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toFloatList(value: String): List<Float> {
        return try {
            Gson().fromJson<List<Float>>(value)
        } catch (e: Exception) {
            listOf()
        }
    }

    @TypeConverter
    fun fromIntList(value: List<Int>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toIntList(value: String): List<Int> {
        return try {
            Gson().fromJson<List<Int>>(value)
        } catch (e: Exception) {
            listOf()
        }
    }

    inline fun <reified T> Gson.fromJson(json: String): T =
        fromJson<T>(json, object : TypeToken<T>() {}.type)
}