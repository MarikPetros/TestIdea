/*
Classes in this package if the project is extendable
and there could be opportunities for using these classes by other parts, e.g. other ViewModels.
For a small app no need for them, and Repository can be provided directly to the ViewModel as argument.
 */
package com.example.testidea.core.domain

import com.example.testidea.core.model.toEntity
import com.example.testidea.data.ProductRepository

class UpdateProductData(
    private val repository: ProductRepository,
    private val id: Int,
    private val amount: Int
) {
    suspend operator fun invoke() = repository.updateProductAmount(id = id, amount = amount)
}