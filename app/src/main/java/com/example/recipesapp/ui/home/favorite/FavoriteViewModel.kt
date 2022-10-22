package com.example.recipesapp.ui.home.favorite

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipesapp.domain.model.RecipeData
import com.example.recipesapp.domain.repository.FavoriteRecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteRecipeRepository: FavoriteRecipeRepository
) : ViewModel() {

    var favoriteRecipes by mutableStateOf<List<RecipeData>>(emptyList())
        private set

    private var getFavoriteRecipesJob: Job? = null


    init {
        getFavoriteRecipes()
    }

    private fun getFavoriteRecipes() {
        getFavoriteRecipesJob?.cancel()

        getFavoriteRecipesJob = favoriteRecipeRepository.getFavoriteRecipes().onEach { recipes ->
            favoriteRecipes = recipes
        }.launchIn(viewModelScope)
    }
}