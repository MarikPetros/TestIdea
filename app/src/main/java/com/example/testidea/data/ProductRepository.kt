package com.example.testidea.data

import com.example.testidea.data.db.ProductDao
import com.example.testidea.data.db.ProductEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ProductRepository(
    private val productDao: ProductDao,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun insertAll(products: List<ProductEntity>) {
        withContext(defaultDispatcher) {
            productDao.insertAll(products)
        }
    }

    suspend fun updateProductAmount(id: Int, amount: Int) {
        withContext(Dispatchers.IO) {
            productDao.updateProductAmountById(id, amount)
        }
    }

    suspend fun deleteProduct(id: Int) {
        withContext(defaultDispatcher) {
            productDao.deleteProduct(id)
        }
    }

    fun getAllProducts(): Flow<List<ProductEntity>> {
        return productDao.getAllProducts()
    }

    fun getProductsBySearchQuery(text: String): Flow<List<ProductEntity>> {
        return productDao.getProductsBySearchQuery(text)
    }
}