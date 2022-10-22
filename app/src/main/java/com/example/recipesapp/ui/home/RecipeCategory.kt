package com.example.recipesapp.ui.home

import com.example.recipesapp.R
import com.example.recipesapp.ui.home.RecipeCategory.*

enum class RecipeCategory(var value: String, var icon: Int) {
    BEVERAGE("beverage", R.drawable.ic_milk_fruit),
    SNACK("snack", R.drawable.ic_french_fries),
    SOUP("soup", R.drawable.ic_picnic_basket),
    DESSERT("dessert", R.drawable.ic_cupcake),
    SAUCE("sauce", R.drawable.ic_condiment),
    SALAD("salad", R.drawable.ic_grocery_basket),
    APPETIZER("appetizer", R.drawable.ic_food_and_drink),
    BREAKFAST("breakfast", R.drawable.ic_sandwich),
    MAIN_COURSE("main course", R.drawable.ic_serving_platter),
    BREAD("bread", R.drawable.ic_bread),

}

fun getAllRecipeCategories(): List<RecipeCategory> {
    return listOf(
        BEVERAGE,
        SNACK,
        SOUP,
        DESSERT,
        SAUCE,
        SALAD,
        APPETIZER,
        BREAKFAST,
        MAIN_COURSE,
        BREAD
    )
}

fun getRecipeCategory(value: String): RecipeCategory {
    val map = RecipeCategory.values().associateBy(RecipeCategory::value)
    return map[value] ?: BEVERAGE
}