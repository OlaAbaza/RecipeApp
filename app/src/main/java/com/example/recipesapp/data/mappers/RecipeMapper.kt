package com.example.recipesapp.data.mappers

import com.example.recipesapp.data.network.ExtendedIngredient
import com.example.recipesapp.data.network.Recipe
import com.example.recipesapp.data.network.RecipeDto
import com.example.recipesapp.data.network.Step
import com.example.recipesapp.domain.model.Ingredient
import com.example.recipesapp.domain.model.RecipeData
import com.example.recipesapp.domain.model.Steps

fun RecipeDto.toRecipeData(): List<RecipeData>? {
    return recipes?.mapNotNull { recipe ->
        recipe?.toRecipeData()
    }
}

fun Recipe.toRecipeData(): RecipeData {
    return RecipeData(
        vegetarian = vegetarian,
        vegan = vegan,
        glutenFree = glutenFree,
        aggregateLikes = aggregateLikes,
        title = title,
        id = id,
        image = image,
        readyInMinutes = readyInMinutes,
        summary = summary,
        //steps = analyzedInstructions?.get(0)?.steps?.map { it?.toStepsData() },
        ingredients = extendedIngredients?.map { it?.toIngredient() }

    )
}


fun Step.toStepsData(): Steps {
    return Steps(
        length,
        number,
        step
    )
}

fun ExtendedIngredient.toIngredient(): Ingredient {
    return Ingredient(
        amount,
        id,
        image,
        name,
        original,
        originalName,
    )
}
