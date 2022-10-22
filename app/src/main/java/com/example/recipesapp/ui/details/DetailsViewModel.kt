package com.example.recipesapp.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipesapp.domain.model.RecipeData
import com.example.recipesapp.domain.repository.FavoriteRecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val favoriteRecipeRepository: FavoriteRecipeRepository
) : ViewModel() {

    fun saveRecipe(recipeData: RecipeData) {
        viewModelScope.launch {
            favoriteRecipeRepository.insertRecipe(recipeData)
        }
    }

    fun deleteRecipe(recipeData: RecipeData) {
        viewModelScope.launch {
            favoriteRecipeRepository.deleteRecipe(recipeData)
        }
    }
}