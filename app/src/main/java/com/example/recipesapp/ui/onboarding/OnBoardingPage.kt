package com.example.recipesapp.ui.onboarding

import androidx.compose.ui.graphics.Color
import com.example.recipesapp.R

sealed class OnBoardingPage(
    var image: Int,
    var title: String,
    var desc: String,
    var backgroundColor: Color,
    var mainColor: Color = Color(0xFF22A45D)
) {
    object FirstPage : OnBoardingPage(
        R.mipmap.orange,
        "Hmmm, Healthy Food",
        "A variety of healthy foods made by the best chefs. Ingredients are easy to find.",
        backgroundColor = Color(0xFF0195D1),
        mainColor = Color(0xFF0174B5)

    )

    object SecondPage : OnBoardingPage(
        R.mipmap.avocado_tree,
        "Let’s Cook Something Unique Today",
        "Get a million of beautiful and delicious recipes around the world",
        backgroundColor = Color(0xFFE9AF00),
        mainColor = Color(0xFFD19300)

    )


    object ThirdPage : OnBoardingPage(
        R.mipmap.avocado,
        "Enjoy Cooking",
        "Are you ready to make a dish for your friends or family",
        backgroundColor = Color(0xFF969F42),
        mainColor = Color(0xFF334614)
    )
}

val onBoardingPages = listOf(
    OnBoardingPage.FirstPage,
    OnBoardingPage.SecondPage,
    OnBoardingPage.ThirdPage
)