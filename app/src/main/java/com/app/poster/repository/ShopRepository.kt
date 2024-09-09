package com.app.poster.repository

import android.util.Log
import com.app.poster.api.Resource
import com.app.poster.data.products.ProductsResponse
import com.app.poster.service.ShopService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ShopRepository(private val api: ShopService) {
    suspend fun getProducts(): Flow<Resource<ProductsResponse>> = flow {
        Log.d("Repository", "getPosts")
        try {
            emit(Resource.Loading) // Emit loading state
            val response = api.getProducts() // Make the network request
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown Error")) // Emit error state
        }
    }.flowOn(Dispatchers.IO)


}