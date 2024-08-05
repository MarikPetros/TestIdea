package com.example.testidea.ui.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testidea.core.domain.GetProductDataFromDB
import com.example.testidea.core.domain.GetProductDataFromRemote
import com.example.testidea.core.domain.GetProductsBySearchQuery
import com.example.testidea.core.domain.RemoveProductData
import com.example.testidea.core.domain.UpdateProductData
import com.example.testidea.core.model.Product
import com.example.testidea.data.db.ProductEntity
import com.example.testidea.data.db.toProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductViewModel(
    private val getterFromRemote: GetProductDataFromRemote,
    getterFromDB: GetProductDataFromDB,
    private val getProductsBySearchQuery: GetProductsBySearchQuery,
    private val updateProductData: UpdateProductData,
    private val removeProductData: RemoveProductData
) : ViewModel() {

    fun addDataToDB() {
        viewModelScope.launch { getterFromRemote() }
    }

    private val productsFlow: Flow<List<Product>> =
        getterFromDB().map { entityList: List<ProductEntity> ->
            entityList.map { entity: ProductEntity -> entity.toProduct() }
        }

    val productsStateFlow: StateFlow<List<Product>> = productsFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _searchResultsEntity = MutableStateFlow<List<ProductEntity>>(emptyList())
    private val _searchResults = _searchResultsEntity.map { list: List<ProductEntity> ->
        list.map { productEntity -> productEntity.toProduct() }
    }
    val searchResults: StateFlow<List<Product>> = _searchResults.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun searchProducts(query: String) {
        viewModelScope.launch {
            val searcher = getProductsBySearchQuery.searchProduct(string = query)
            searcher.collect { results ->
                _searchResultsEntity.value = results
            }
        }
    }

    fun updateProduct(id: Int, amount: Int) {
        viewModelScope.launch {
            updateProductData.updateProductAmount(id = id, amount = amount)
        }
    }

    fun removeProduct(id: Int) {
        viewModelScope.launch {
            removeProductData.removeProduct(id = id)
        }
    }
}