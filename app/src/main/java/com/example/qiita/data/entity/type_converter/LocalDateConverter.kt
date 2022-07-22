package com.example.qiita.data.entity.type_converter

import androidx.room.TypeConverter
import java.time.LocalDate

class LocalDateConverter {

    @TypeConverter
    fun fromLocalDate(value: Long?): LocalDate? {
        return if (value == null) {
            null
        } else {
            LocalDate.ofEpochDay(value)
        }
    }

    @TypeConverter
    fun localDateToLong(localDate: LocalDate?): Long? {
        return localDate?.toEpochDay()
    }
}