package com.example.recipesapp.ui.home.favorite

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
import com.example.recipesapp.ui.components.RecipesGrid
import com.example.recipesapp.ui.theme.LightGreen
import com.example.recipesapp.ui.theme.spacing


@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = hiltViewModel(),
    onSelectRecipe: (RecipeData) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(
            text = stringResource(id = R.string.favorite),
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(MaterialTheme.spacing.small),
            textAlign = TextAlign.Start,
            fontSize = 25.sp
        )

        if (viewModel.favoriteRecipes.isEmpty())
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
                    text = "You do not have any recipes",
                    color = LightGreen,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        else {
            RecipesGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.small),
                recipes = viewModel.favoriteRecipes
            ) {
                onSelectRecipe(it)
            }
        }

    }
}
