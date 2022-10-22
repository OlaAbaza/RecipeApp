package com.example.recipesapp.ui.home

import com.example.recipesapp.domain.model.RecipeData

sealed class HomeViewState {
    object Empty : HomeViewState()
    object Loading : HomeViewState()
    data class Success(val data: List<RecipeData>) : HomeViewState()
    data class Error(val message: String?) : HomeViewState()
}
