package com.example.testidea.data

import com.example.testidea.core.model.Product

/**
 * A class for storing predefined data.
 * Instead of this could be a remote data source.
 */
class DummyData {
    val data: List<Product> = listOf(
        Product(1, "iPhone 13", 1633046400000, mutableListOf("Телефон", "Новый", "Распродажа"), 15),
        Product(2, "Samsung Galaxy S21", 1633132800000, mutableListOf("Телефон", "Хит"), 30),
        Product(
            3,
            "PlayStation 5",
            1633219200000,
            mutableListOf("Игровая приставка", "Акция", "Распродажа"),
            7
        ),
        Product(
            4,
            "LG OLED TV",
            1633305600000,
            mutableListOf("Телевизор", "Эксклюзив", "Ограниченный"),
            22
        ),
        Product(
            5,
            "Apple Watch Series 7",
            1633392000000,
            mutableListOf("Часы", "Новый", "Рекомендуем"),
            0
        ),
        Product(
            6,
            "Xiaomi Mi 11",
            1633478400000,
            mutableListOf("Телефон", "Скидка", "Распродажа"),
            5
        ),
        Product(7, "MacBook Air M1|", 1633564800000, mutableListOf("Ноутбук", "Тренд"), 12),
        Product(
            8,
            "Amazon Kindle Paperwhite",
            1633651200000,
            mutableListOf("Электронная книга", "Последний шанс", "Ограниченный"),
            18
        ),
        Product(9, "Fitbit Charge 5", 1633737600000, mutableListOf(), 27),
        Product(10, "GoPro Hero 10", 1633824000000, mutableListOf("Камера", "Эксклюзив"), 25),
    )
}