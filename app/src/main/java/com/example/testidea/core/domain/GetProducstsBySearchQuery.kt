package com.example.testidea.core.domain

import com.example.testidea.data.ProductRepository

class GetProducstsBySearchQuery(private val repository: ProductRepository, private val string: String) {
    suspend operator fun invoke() = repository.getProductsBySearchQuery(string)
}