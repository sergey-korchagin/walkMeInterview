package com.skorch.walkmeinterview.data.remote

import retrofit2.http.GET

interface ArticleApiService {
    @GET("news/sport")
    suspend fun getArticles(): NewsResponse
}