package com.app.poster.data.model

import com.app.poster.data.products.Product

data class CatProduct(
    val categoryName: String?,
    val productList: List<Product>
)
