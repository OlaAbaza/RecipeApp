package com.example.recipesapp.domain.repository

import com.example.recipesapp.domain.model.RecipeData
import kotlinx.coroutines.flow.Flow

interface FavoriteRecipeRepository {

    fun getFavoriteRecipes(): Flow<List<RecipeData>>

    suspend fun insertRecipe(recipe: RecipeData)

    suspend fun deleteRecipe(recipeData: RecipeData)

}