package com.example.recipesapp.data.database

import androidx.room.*
import com.example.recipesapp.domain.model.RecipeData
import kotlinx.coroutines.flow.Flow


@Dao
interface RecipeDao {

    @Query("SELECT * FROM recipedata")
    fun getFavoriteRecipes(): Flow<List<RecipeData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipeData: RecipeData)

    @Delete
    suspend fun deleteRecipe(recipeData: RecipeData)
}