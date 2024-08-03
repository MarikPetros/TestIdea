package com.example.testidea.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ProductEntity::class], version = 1)
abstract class ProductsDB : RoomDatabase() {

    abstract fun productDao(): ProductDao
}