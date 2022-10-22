package com.example.recipesapp.data.repository

import com.example.recipesapp.domain.util.Resource
import com.example.recipesapp.data.mappers.toRecipeData
import com.example.recipesapp.data.network.RecipeApi
import com.example.recipesapp.domain.model.RecipeData
import com.example.recipesapp.domain.repository.RecipeRepository
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(private val recipeApi: RecipeApi) :
    RecipeRepository {
    override suspend fun getRandomRecipesDate(
        number: Int,
        tags: List<String>
    ): Resource<List<RecipeData>> {
        return try {
            Resource.Success((recipeApi.getRandomRecipes(number, tags).toRecipeData()))

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")

        }
    }
}