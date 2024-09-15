package com.app.poster.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.poster.api.ApiClient
import com.app.poster.api.Resource
import com.app.poster.data.PostResponse
import com.app.poster.data.model.CatProduct
import com.app.poster.data.payloads.CreateProductPayload
import com.app.poster.data.products.Product
import com.app.poster.data.products.ProductsResponse
import com.app.poster.repository.ShopRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {
    //    val productsResponse = MutableStateFlow<ProductsResponse?>(null)
    val catProductList = MutableStateFlow<List<CatProduct>>(emptyList())
    val categories = MutableStateFlow<List<String>>(emptyList())
    val isLoading = MutableStateFlow(true)
    private val repository = ShopRepository(ApiClient.api)


    init {
        getCategories()
    }


    private fun getCategories() {
        viewModelScope.launch {
            repository.getCategories().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        isLoading.value = true // Set loading state
                    }

                    is Resource.Success -> {
                        isLoading.value = false // Stop loading
                        categories.value = resource.data // Set news data
                        categories.value.forEach {
                            Log.d("Category", it)

                            val categoryName = it

                            viewModelScope.launch {
                                repository.getProductsByCategory(name = categoryName)
                                    .collect { resource ->
                                        when (resource) {
                                            is Resource.Loading -> {
                                                isLoading.value = true // Set loading state
                                            }

                                            is Resource.Success -> {
                                                isLoading.value = false // Stop loading

                                                val catProduct = CatProduct(
                                                    categoryName = categoryName,
                                                    productList = resource.data
                                                )
                                                catProductList.value =
                                                    catProductList.value.toMutableList().apply {
                                                        add(catProduct)
                                                    }
                                            }

                                            is Resource.Error -> {
                                                isLoading.value = false // Stop loading

                                            }
                                        }
                                    }
                            }

                        }


                    }

                    is Resource.Error -> {
                        isLoading.value = false // Stop loading
                    }
                }
            }
        }
    }


}