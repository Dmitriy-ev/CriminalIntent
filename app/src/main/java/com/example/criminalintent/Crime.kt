package com.example.criminalintent

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.Date
import java.util.UUID

@Entity
data class Crime(
    @PrimaryKey
    val id :UUID = UUID.randomUUID(),
    var title: String  = "",
    var data: Date = Date(),
    var isSolved: Boolean = false,

)