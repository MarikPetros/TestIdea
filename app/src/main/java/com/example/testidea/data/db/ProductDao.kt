package com.example.testidea.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProductDao {
    // Though in this test don't required, but OnConflictStrategy is useful in case of getting data from remote source.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg products: ProductEntity)

    @Update
    fun updateProduct(product: ProductEntity)

    @Delete
    fun deleteProduct(product: ProductEntity)

    @Query("SELECT * FROM products")
    fun getAllProducts(): List<ProductEntity>

    @Query("SELECT * FROM products WHERE name LIKE '%' || :text || '%'")
    fun getProductsByName(text: String): List<ProductEntity>
}