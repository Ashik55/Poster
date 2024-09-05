package com.app.poster

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.app.poster.api.ApiClient
import com.app.poster.api.Resource
import com.app.poster.repository.PostRepository
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val repository = PostRepository(ApiClient.api)

        lifecycleScope.launch {
            repository.getPosts().collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        Log.d("response", "Error")
                    }
                    Resource.Loading -> {
                        Log.d("response", "Loading")
                    }
                    is Resource.Success -> {
                        val data = resource.data
                        Log.d("response", data.size.toString())

                    }
                }
            }
        }


    }
}