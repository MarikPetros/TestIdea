package com.example.testidea.core.model

import com.example.testidea.data.db.ProductEntity

data class Product(
    val id: Int,
    val name: String,
    var time: Int,
    var tags: MutableList<String>,
    var amount: Int
)

fun Product.toEntity(): ProductEntity {
    return ProductEntity(
        id = id,
        name = name,
        time = time, //
        tags = tags,
        amount = amount
    )
}