package com.example.criminalintent.database

import androidx.room.TypeConverter
import java.util.Date
import java.util.UUID

class CrimeTypeConverters {
    @TypeConverter
    fun fromDate(date: Date?): Long? = date?.time
    @TypeConverter
    fun toDate(mill: Long?): Date? = mill?.let { Date(it) }
    @TypeConverter
    fun fromUUID(uuid: UUID?): String? = uuid?.toString()
    @TypeConverter
    fun toUUID(uuid: String?): UUID = UUID.fromString(uuid)
}