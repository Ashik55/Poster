package com.app.poster.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.poster.api.ApiClient
import com.app.poster.api.Resource
import com.app.poster.data.PostResponse
import com.app.poster.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {

    // Saving only NewsResponse instead of the Resource wrapper
    val postResponse = MutableStateFlow<PostResponse?>(null)

    // Separate variables to handle loading and error states
    val isLoading = MutableStateFlow(true)

    // Separate variables to handle loading and error states
    val errorMessage = MutableStateFlow<String?>(null)

    private val repository = PostRepository(ApiClient.api)


    init {
        getPostList()
    }

    private fun getPostList() {
        viewModelScope.launch {
            repository.getPosts().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        isLoading.value = true // Set loading state
                    }

                    is Resource.Success -> {
                        isLoading.value = false // Stop loading
                        postResponse.value = resource.data // Set news data


                    }

                    is Resource.Error -> {
                        isLoading.value = false // Stop loading
                        errorMessage.value = resource.message // Set error message
                    }
                }
            }
        }
    }
}