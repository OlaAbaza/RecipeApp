package com.example.recipesapp.ui.navigation

sealed class MainDestinations(val route: String) {
    object OnBoarding : MainDestinations(route = "onboarding_screen")
    object Home : MainDestinations(route = "home_screen")
    object Details : MainDestinations(route = "details_screen")
}