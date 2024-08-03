package com.example.testidea.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.testidea.core.model.Product

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    var time: Int,
    val tags: MutableList<String>,
    var amount: Int
)

fun ProductEntity.toDataClass(): Product {
    return Product(
        id = id,
        name = name,
        time = time,
        tags = tags,   // this line can cause problems because of val/var
        amount = amount
    )
}