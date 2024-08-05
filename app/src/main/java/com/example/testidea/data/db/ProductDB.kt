package com.example.testidea.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ProductEntity::class], version = 1, exportSchema = false)
@TypeConverters(StringListConverter::class)
abstract class ProductsDB : RoomDatabase() {

    abstract fun productDao(): ProductDao
}