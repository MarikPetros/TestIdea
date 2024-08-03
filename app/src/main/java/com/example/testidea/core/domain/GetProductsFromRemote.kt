/*
Classes in this package if the project is extendable
and there could be opportunities for using these classes by other parts, e.g. other ViewModels.
For a small app no need for them, and Repository can be provided directly to the ViewModel as argument.
 */package com.example.testidea.core.domain

import com.example.testidea.data.DummyData
import com.example.testidea.data.ProductRepository

/*
In real projects data will be retrieved from remote source, supposedly.
Accordingly, the retriever class will be used here as argument instead of [DummyData].
 */
class GetProductsFromRemote(
    private val repository: ProductRepository,
    private val dummyData: DummyData,
) {
    suspend operator fun invoke() = repository.insertAll(dummyData.data)
}