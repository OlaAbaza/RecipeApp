package com.example.recipesapp.data.repository

import com.example.recipesapp.data.database.RecipeDao
import com.example.recipesapp.domain.model.RecipeData
import com.example.recipesapp.domain.repository.FavoriteRecipeRepository
import kotlinx.coroutines.flow.Flow

class FavoriteRecipeRepositoryImpl(private val recipeDao: RecipeDao) : FavoriteRecipeRepository {
    override fun getFavoriteRecipes(): Flow<List<RecipeData>> {
        return recipeDao.getFavoriteRecipes()
    }

    override suspend fun insertRecipe(recipe: RecipeData) {
        recipeDao.insertRecipe(recipe)
    }

    override suspend fun deleteRecipe(recipeData: RecipeData) {
        recipeDao.deleteRecipe(recipeData)
    }
}