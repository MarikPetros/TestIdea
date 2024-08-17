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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductViewModel(
    private val getterFromRemote: GetProductDataFromRemote,
    private val getterFromDB: GetProductDataFromDB,
    private val getProductsBySearchQuery: GetProductsBySearchQuery,
    private val updateProductData: UpdateProductData,
    private val removeProductData: RemoveProductData
) : ViewModel() {

    init {
        viewModelScope.launch {
            getterFromRemote()
            getterFromDB().collect { productEntityList ->
                _productsFlow.value = productEntityList
            }
        }
    }

    private val _productsFlow = MutableStateFlow<List<ProductEntity>>(emptyList())
    val productsStateFlow: StateFlow<List<Product>> =
        _productsFlow.map { entityList: List<ProductEntity> ->
            entityList.map { entity: ProductEntity -> entity.toProduct() }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _searchResultsEntity = MutableStateFlow<List<ProductEntity>>(emptyList())
    val searchResultsStateFlow = _searchResultsEntity.map { list: List<ProductEntity> ->
        list.map { productEntity -> productEntity.toProduct() }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun searchProducts(query: String) {
        viewModelScope.launch {
            getProductsBySearchQuery.searchProduct(string = query).collect {
                _searchResultsEntity.value = it
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