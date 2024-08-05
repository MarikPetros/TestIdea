package com.example.testidea.core.domain

import com.example.testidea.data.ProductRepository

class GetProductsBySearchQuery(private val repository: ProductRepository) {
    fun searchProduct(string: String) = repository.getProductsBySearchQuery(string)
}