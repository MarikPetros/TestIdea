package com.example.testidea

import androidx.room.Room
import com.example.testidea.data.ProductRepository
import com.example.testidea.data.db.ProductsDB
import com.example.testidea.ui.view_models.ProductViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val ideaAppModule = module{
    single {
        Room.databaseBuilder(
            androidApplication(),
            ProductsDB::class.java,
            "products_database"
        ).build()
    }
    single { get<ProductsDB>().productDao() }
    single{ ProductRepository(get()) }
    viewModel { ProductViewModel(get()) }
}