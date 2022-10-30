package com.exercise.cardstore.core.util

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

object DateConverter {
    private val simpleDateFormat = SimpleDateFormat("dd/MM/yy")

    @TypeConverter
    fun toDate(stringDate: String?): Date? {
        return stringDate?.let {  simpleDateFormat.parse(it)}
    }

    @TypeConverter
    fun toTimestamp(date: Date?): String? {
        val dateTimeFormat = simpleDateFormat
        return date?.let { dateTimeFormat.format(it) }
    }
}