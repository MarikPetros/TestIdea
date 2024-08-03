package com.example.testidea.data

import com.example.testidea.core.model.Product

/**
 * A class for storing predefined data.
 * Instead of this could be a remote data source.
 */
class DummyData {
    val data: List<Product> = listOf(
        Product(1,"iPhone 13", 0, mutableListOf("Телефон", "Новый", "Распродажа"),15),
        Product(2,"Samsung Galaxy S21", 0, mutableListOf("Телефон", "Хит"),30),
        Product(3,"PlayStation 5", 0, mutableListOf("Игровая приставка", "Акция", "Распродажа"),7),
        Product(4,"LG OLED TV", 0, mutableListOf("Телевизор", "Эксклюзив", "Ограниченный"),22),
        Product(5,"Apple Watch Series 7", 0, mutableListOf("Часы", "Новый", "Рекомендуем"),0),
        Product(6,"Xiaomi Mi 11", 0, mutableListOf("Телефон", "Скидка", "Распродажа"),5),
        Product(7,"MacBook Air M1|", 0, mutableListOf("Ноутбук", "Тренд"),12),
        Product(8,"Amazon Kindle Paperwhite", 0, mutableListOf("Электронная книга", "Последний шанс", "Ограниченный"),18),
        Product(9,"Fitbit Charge 5", 0, mutableListOf(),27),
        Product(10,"GoPro Hero 10", 0, mutableListOf("Камера", "Эксклюзив"),25),
    )
}