package com.example.recipesapp.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipesapp.R
import com.example.recipesapp.domain.model.RecipeData
import com.example.recipesapp.ui.components.CategoryBar
import com.example.recipesapp.ui.components.LottieLoadingAnimation
import com.example.recipesapp.ui.components.RecipesGrid
import com.example.recipesapp.ui.theme.LightGreen
import com.example.recipesapp.ui.theme.spacing

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onSelectRecipe: (RecipeData) -> Unit,
) {
    val state = viewModel.state

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HeaderSection(Modifier.fillMaxWidth())

        AnimatedVisibility(visible = state != HomeViewState.Loading) {
            CategoryBar(categories = getAllRecipeCategories(),
                selectedCategory = viewModel.selectedCategory,
                onSelectedChanged = {
                    viewModel.onEvent(HomeViewEvent.ChangeCategory(it))
                })
        }

        when (state) {
            is HomeViewState.Empty,
            is HomeViewState.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.CenterHorizontally),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_chef),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                    )
                    Text(
                        text = "Oops, we can’t show you any recipes yet",
                        color = LightGreen,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

            }
            HomeViewState.Loading -> {
                LottieLoadingAnimation(
                    Modifier
                        .fillMaxSize()
                        .align(Alignment.CenterHorizontally)
                )
            }
            is HomeViewState.Success -> {
                RecipesGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(MaterialTheme.spacing.small),
                    recipes = state.data
                ) {
                    onSelectRecipe(it)
                }
            }
        }

    }
}

@Composable
fun HeaderSection(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(MaterialTheme.spacing.small),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.discover_recipe),
            color = Color.Black,
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily.SansSerif,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = stringResource(R.string.header_subtitle),
            color = Color.Black.copy(alpha = 0.4f),
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = FontFamily.SansSerif,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start =MaterialTheme.spacing.extraSmall)
        )

    }
}
