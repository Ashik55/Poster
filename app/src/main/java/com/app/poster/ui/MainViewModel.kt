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
    private val _postResponse = MutableStateFlow<PostResponse?>(null)
    val postResponse: StateFlow<PostResponse?> = _postResponse

    // Separate variables to handle loading and error states
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val repository = PostRepository(ApiClient.api)


    init {
        getPostList()
    }

    private fun getPostList() {
        viewModelScope.launch {
            repository.getPosts().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _isLoading.value = true // Set loading state
                    }
                    is Resource.Success -> {
                        _isLoading.value = false // Stop loading
                        _postResponse.value = resource.data // Set news data
                    }
                    is Resource.Error -> {
                        _isLoading.value = false // Stop loading
                        _errorMessage.value = resource.message // Set error message
                    }
                }
            }
        }
    }
}