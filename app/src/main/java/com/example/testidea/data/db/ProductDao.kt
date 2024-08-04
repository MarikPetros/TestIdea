package com.example.testidea.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    // Though in this test don't required, but OnConflictStrategy is useful in case of getting data from remote source.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<ProductEntity>)

    @Query("UPDATE products SET amount = :amount WHERE id= :id")
    suspend fun updateProductAmountById(id: Int, amount: Int)

    @Delete
    @Query("DELETE FROM products WHERE id= :id")
    suspend fun deleteProduct(id: Int)

    @Query("SELECT * FROM products")
    suspend fun getAllProducts(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM products WHERE name LIKE '%' || :text || '%'")
    suspend fun getProductsBySearchQuery(text: String): Flow<List<ProductEntity>>
}