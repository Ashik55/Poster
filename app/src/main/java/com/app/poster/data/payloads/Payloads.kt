package com.app.poster.data.payloads

data class CreateProductPayload(
    val category: String?,
    val description: String?,
    val image: String?,
    val price: Number?,
    val title: String?
)