/*
Classes in this package if the project is extendable
and there could be opportunities for using these classes by other parts, e.g. other ViewModels.
For a small app no need for them, and Repository can be provided directly to the ViewModel as argument.
 */
package com.example.testidea.core.domain

import com.example.testidea.core.model.toEntity
import com.example.testidea.data.DummyData
import com.example.testidea.data.ProductRepository

/*
In real projects data will be retrieved from remote source, supposedly.
Accordingly, the retriever class instance will be used here as argument instead of [dummyData].
 */
class GetProductDataFromRemote(
    private val repository: ProductRepository,
    dummyData: DummyData,
) {
    private val productEntityList = dummyData.data.map { product ->
        product.toEntity().apply {
            time = System.currentTimeMillis().toInt()
        }
    }
    suspend operator fun invoke() = repository.insertAll(productEntityList)
}