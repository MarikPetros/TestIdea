package com.example.testidea.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    // Though in this test don't required, but OnConflictStrategy is useful in case of getting data from remote source.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg products: ProductEntity)

    @Upsert
    suspend fun updateProduct(product: ProductEntity)

    @Delete
    suspend fun deleteProduct(product: ProductEntity)

    @Query("SELECT * FROM products")
    fun getAllProducts(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM products WHERE name LIKE '%' || :text || '%'")
    fun getProductsByName(text: String): Flow<List<ProductEntity>>
}