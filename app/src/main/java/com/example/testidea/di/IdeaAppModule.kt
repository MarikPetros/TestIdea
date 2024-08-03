package com.example.testidea.di

import androidx.room.Room
import com.example.testidea.core.domain.GetProducts
import com.example.testidea.core.domain.GetProductsFromRemote
import com.example.testidea.core.domain.RemoveProduct
import com.example.testidea.core.domain.UpdateProduct
import com.example.testidea.data.DummyData
import com.example.testidea.data.ProductRepository
import com.example.testidea.data.db.ProductsDB
import com.example.testidea.ui.view_models.ProductViewModel
import kotlinx.coroutines.Dispatchers
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
    single { DummyData() }
    single { get<ProductsDB>().productDao() }
    single{ ProductRepository(get(), Dispatchers.IO) }
    single { GetProductsFromRemote(get(), get()) }
    single { GetProducts(get()) }
    single { RemoveProduct(get()) }
    single { UpdateProduct(get()) }
    viewModel { ProductViewModel(get()) }
}

