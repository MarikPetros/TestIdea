package com.example.testidea.data.db

import androidx.room.TypeConverter

class StringListConverter {
    @TypeConverter
    fun fromString(value: String): MutableList<String> {
        return value.split(",").toMutableList()
    }

    @TypeConverter
    fun toString(list: MutableList<String>): String {
        return list.joinToString(",")
    }
}