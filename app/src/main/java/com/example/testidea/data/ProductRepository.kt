package com.example.testidea.data

import com.example.testidea.core.model.Product
import com.example.testidea.core.model.toEntity
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

    suspend fun insertAll(products: List<Product>) {
        val productEntityList = products.map { product -> product.toEntity() }
        withContext(defaultDispatcher) {
            productDao.insertAll(productEntityList)
        }
    }

    suspend fun updateProduct(product: ProductEntity) {
        withContext(defaultDispatcher) {
            productDao.updateProduct(product)
        }
    }

    suspend fun deleteProduct(product: ProductEntity) {
        withContext(defaultDispatcher) {
            productDao.deleteProduct(product)
        }
    }

    suspend fun getAllProducts(): Flow<List<ProductEntity>> {
        return withContext(defaultDispatcher) {
            productDao.getAllProducts()
        }
    }

    suspend fun getProductsByName(text: String): Flow<List<ProductEntity>> {
        return withContext(defaultDispatcher) {
            productDao.getProductsByName(text)
        }
    }
}