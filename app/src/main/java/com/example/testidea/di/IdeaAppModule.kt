package com.example.testidea.di

import androidx.room.Room
import com.example.testidea.core.domain.GetProductDataFromDB
import com.example.testidea.core.domain.GetProductDataFromRemote
import com.example.testidea.core.domain.GetProductsBySearchQuery
import com.example.testidea.core.domain.RemoveProductData
import com.example.testidea.core.domain.UpdateProductData
import com.example.testidea.data.DummyData
import com.example.testidea.data.ProductRepository
import com.example.testidea.data.db.ProductsDB
import com.example.testidea.ui.view_models.ProductViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ideaAppModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            ProductsDB::class.java,
            "products_database"
        ).build()
    }
    single { get<ProductsDB>().productDao() }
    single { DummyData() }
    single { ProductRepository(get(), Dispatchers.IO) }

    single { GetProductDataFromRemote(get(), get()) }
    single { GetProductDataFromDB(get()) }
    single { RemoveProductData(get()) }
    single { GetProductsBySearchQuery(get()) }
    single { UpdateProductData(get()) }

    viewModel { ProductViewModel(get(), get(), get(), get(), get()) }
}

