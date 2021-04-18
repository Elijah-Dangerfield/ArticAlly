package com.dangerfield.artically.data.network.service

import com.dangerfield.artically.BuildConfig
import com.dangerfield.artically.data.network.model.TopHeadlinesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticlesService {

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "us",
        @Query("apiKey") apiKey: String = BuildConfig.apiKey
    ): TopHeadlinesResponse
}