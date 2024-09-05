package com.app.poster.service

import com.app.poster.data.PostResponse
import retrofit2.http.GET


interface PostService {
    @GET("posts")
    suspend fun getPosts(): PostResponse
}