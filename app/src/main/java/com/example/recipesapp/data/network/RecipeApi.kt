package com.example.recipesapp.data.network

import com.example.recipesapp.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApi {
    @GET("random?apiKey=${BuildConfig.API_KEY}")
    suspend fun getRandomRecipes(
        @Query("number") number: Int,
        @Query("tags") tags: List<String>
    ): RecipeDto
}

