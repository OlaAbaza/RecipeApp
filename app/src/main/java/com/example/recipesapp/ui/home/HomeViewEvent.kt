package com.example.recipesapp.ui.home

sealed class HomeViewEvent {
    data class ChangeCategory(val selectedCategory: RecipeCategory) : HomeViewEvent()
}