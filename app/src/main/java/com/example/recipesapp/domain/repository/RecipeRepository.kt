package com.example.recipesapp.domain.repository

import com.example.recipesapp.domain.util.Resource
import com.example.recipesapp.domain.model.RecipeData

interface RecipeRepository {
    suspend fun getRandomRecipesDate(
        number: Int,
        tags: List<String>
    ): Resource<List<RecipeData>>
}