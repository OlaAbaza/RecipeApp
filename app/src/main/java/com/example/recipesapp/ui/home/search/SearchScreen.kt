package com.example.recipesapp.ui.home.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipesapp.R
import com.example.recipesapp.domain.model.RecipeData
import com.example.recipesapp.ui.components.LottieLoadingAnimation
import com.example.recipesapp.ui.components.RecipesGrid
import com.example.recipesapp.ui.components.SearchBar
import com.example.recipesapp.ui.home.HomeViewState
import com.example.recipesapp.ui.theme.LightGreen
import com.example.recipesapp.ui.theme.spacing


@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    onSelectRecipe: (RecipeData) -> Unit
) {
    val state = viewModel.state
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(
            text = stringResource(R.string.search),
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(MaterialTheme.spacing.small),
            textAlign = TextAlign.Start,
            fontSize = 25.sp
        )
        SearchBar(query = viewModel.query, onQueryChanged = {
            viewModel.updateSearchQuery(it)
        }) {
            viewModel.getSearchResult()
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
                        text = "No Result",
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

