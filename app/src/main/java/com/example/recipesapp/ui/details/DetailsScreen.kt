package com.example.recipesapp.ui.details

import android.widget.TextView
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.ChipDefaults.chipColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.example.recipesapp.R
import com.example.recipesapp.domain.model.RecipeData
import com.example.recipesapp.ui.theme.LightGreen
import com.example.recipesapp.ui.theme.MintGreen
import com.example.recipesapp.ui.theme.spacing
import com.google.accompanist.placeholder.placeholder


@OptIn(ExperimentalCoilApi::class)
@Composable
fun DetailsScreen(
    upPress: () -> Unit,
    recipeData: RecipeData,
    detailsViewModel: DetailsViewModel = hiltViewModel()
) {
    var isFavoriteSelected by remember {
        mutableStateOf(recipeData.isFavorite)
    }
    val painter = rememberImagePainter(
        data = recipeData.image,
        builder = {
            crossfade(500)
        })

    val painterState = painter.state

    val isImgLoading = painterState is ImagePainter.State.Loading

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val scroll = rememberScrollState(0)

        Box(Modifier.fillMaxWidth()) {

            Image(
                painter = painter,
                contentDescription = "",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .placeholder(visible = isImgLoading, color = Color.Gray)
            )

            IconButton(
                onClick = { upPress() },
                modifier = Modifier.align(Alignment.TopStart)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_chevron_left_24),
                    contentDescription = "back icon",
                    tint = Color.Gray,
                    modifier = Modifier.size(35.dp)

                )
            }
            IconButton(
                onClick = {
                    isFavoriteSelected = !isFavoriteSelected
                    recipeData.isFavorite = isFavoriteSelected
                    if (isFavoriteSelected)
                        detailsViewModel.saveRecipe(recipeData)
                    else
                        detailsViewModel.deleteRecipe(recipeData)
                },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_heart),
                    contentDescription = "favorite icon",
                    tint = if (isFavoriteSelected) LightGreen else Color.Gray,
                    modifier = Modifier.size(35.dp)

                )
            }

        }

        Row(modifier = Modifier.padding(top = MaterialTheme.spacing.extraSmall)) {
            if (recipeData.glutenFree == true)
                InfoChip(name = stringResource(R.string.gluten_free))
            if (recipeData.vegetarian == true)
                InfoChip(name = stringResource(R.string.vegetarian))
            if (recipeData.vegan == true)
                InfoChip(name = stringResource(R.string.vegan))
        }

        Row(
            modifier = Modifier.padding(MaterialTheme.spacing.extraSmall),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = recipeData.title ?: "",
                color = Color.Black,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                textAlign = TextAlign.Start,
                maxLines = 2,
                modifier = Modifier
                    .weight(2f)
                    .padding(start = MaterialTheme.spacing.extraSmall)

            )

            Text(
                text = """${recipeData.readyInMinutes.toString()} mins""",
                color = Color.Black.copy(alpha = 0.5F),
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f),
                maxLines = 1

            )

        }

        Body(summary = recipeData.summary ?: "", scroll = scroll)

        Ingredients(ingredients = recipeData.ingredients?.mapNotNull { it.name }
            ?: emptyList())

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
fun InfoChip(
    modifier: Modifier = Modifier,
    name: String = "Vegan",
) {
    Chip(
        modifier = modifier.padding(horizontal = MaterialTheme.spacing.small),
        onClick = {},
        border = BorderStroke(1.dp, LightGreen),
        colors = chipColors(backgroundColor = MintGreen)

    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.h4,
            color = LightGreen,
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.extraSmall),
            fontSize = 12.sp
        )
    }

}

@Composable
private fun Body(
    scroll: ScrollState,
    modifier: Modifier = Modifier,
    summary: String
) {
    Column(
        modifier = Modifier.verticalScroll(scroll)
    ) {
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Column {
                var seeMore by remember { mutableStateOf(true) }
                AndroidView(
                    modifier = modifier,
                    factory = { context -> TextView(context) },
                    update = {
                        it.text = HtmlCompat.fromHtml(summary, HtmlCompat.FROM_HTML_MODE_COMPACT)
                        it.maxLines = if (seeMore) 5 else Int.MAX_VALUE
                    }
                )
                val textButton = if (seeMore) {
                    stringResource(id = R.string.see_more)
                } else {
                    stringResource(id = R.string.see_less)
                }
                Text(
                    text = textButton,
                    style = MaterialTheme.typography.button,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    modifier = Modifier
                        .heightIn(20.dp)
                        .fillMaxWidth()
                        .padding(top = MaterialTheme.spacing.medium)
                        .clickable {
                            seeMore = !seeMore
                        }
                )
                Spacer(Modifier.height(40.dp))
            }
        }
    }
}

@Composable
private fun Ingredients(
    ingredients: List<String>,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(R.string.ingredients),
        style = MaterialTheme.typography.caption,
        color = LightGreen,
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(start = MaterialTheme.spacing.small)
    )
    Spacer(Modifier.height(4.dp))

    LazyColumn(modifier = modifier.padding(MaterialTheme.spacing.medium)) {
        itemsIndexed(ingredients) { index, item ->
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Circle(circleColor = if (index % 2 == 0) LightGreen else MintGreen)
                Text(
                    text = item,
                    color = Color.Black,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(start = MaterialTheme.spacing.small)
                )
            }

        }
    }
}

@Composable
private fun Circle(
    circleColor: Color = LightGreen,
    circleSize: Dp = 7.dp,
) {
    Box(
        modifier = Modifier
            .size(size = circleSize)
            .clip(shape = CircleShape)
            .background(
                color = circleColor
            )
    )
}