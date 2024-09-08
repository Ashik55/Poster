package com.app.poster.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Visibility
import com.app.poster.R
import com.app.poster.api.ApiClient
import com.app.poster.api.Resource
import com.app.poster.repository.PostRepository
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val repository = PostRepository(ApiClient.api)

        lifecycleScope.launch {
            repository.getPosts().collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        hideProgressBar()
                        Log.d("response", "Error")
                    }

                    Resource.Loading -> {
                        showProgressBar()
                        Log.d("response", "Loading")
                    }

                    is Resource.Success -> {
                        hideProgressBar()
                        val data = resource.data
                        Log.d("response", data.toString())
                        adapter = PostAdapter(data)
                        recyclerView.adapter = adapter
                    }
                }
            }
        }


    }

    fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }
}