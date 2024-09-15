package com.app.poster.repository

import android.util.Log
import com.app.poster.api.Resource
import com.app.poster.data.payloads.CreateProductPayload
import com.app.poster.data.products.Product
import com.app.poster.data.products.ProductsResponse
import com.app.poster.service.ShopService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ShopRepository(private val api: ShopService) {
    suspend fun getProducts(
        limit: Number?, sort: String?
    ): Flow<Resource<ProductsResponse>> = flow {
        try {
            emit(Resource.Loading) // Emit loading state
            val response = api.getProducts(
                limit, sort
            ) // Make the network request
            Log.d("Repository", response.toString())

            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown Error")) // Emit error state
        }
    }.flowOn(Dispatchers.IO)


    suspend fun getCategories(): Flow<Resource<List<String>>> = flow {
        try {
            emit(Resource.Loading)
            val response = api.getAllCategories()
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown Error")) // Emit error state
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getProductsByCategory(
        name: String?
    ): Flow<Resource<List<Product>>> = flow {
        try {
            emit(Resource.Loading) // Emit loading state
            val response = api.getProductsByCategory(name)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown Error")) // Emit error state
        }
    }.flowOn(Dispatchers.IO)


    suspend fun createProduct(
        product: CreateProductPayload
    ): Flow<Resource<Product>> = flow {
        try {
            emit(Resource.Loading) // Emit loading state
            val response = api.createProduct(product) // Make the network request
            Log.d("Repository", response.toString())

            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown Error")) // Emit error state
        }
    }.flowOn(Dispatchers.IO)


}