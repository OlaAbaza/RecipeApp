package com.example.recipesapp.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.example.recipesapp.domain.model.RecipeData
import com.example.recipesapp.ui.home.getAllRecipeCategories
import com.example.recipesapp.ui.theme.RecipesAppTheme
import com.example.recipesapp.ui.theme.spacing
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer

@Composable
fun RecipesGrid(
    modifier: Modifier = Modifier,
    recipes: List<RecipeData>,
    onItemClicked: (RecipeData) -> Unit
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(MaterialTheme.spacing.small)
    ) {

        CustomStaggeredVerticalGrid(
            numColumns = 2,
            modifier = Modifier.padding(MaterialTheme.spacing.extraSmall)
        ) {
            recipes.forEach { recipe ->
                RecipeGridItem(
                    recipeData = recipe,
                    onItemClicked = onItemClicked,
                    modifier = Modifier.padding(MaterialTheme.spacing.small)
                )
            }

        }

    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun RecipeGridItem(
    modifier: Modifier = Modifier,
    recipeData: RecipeData,
    onItemClicked: (RecipeData) -> Unit,
) {
    val painter = rememberImagePainter(
        data = recipeData.image,
        builder = {
            crossfade(500)
        })

    val painterState = painter.state

    val isImgLoading = painterState is ImagePainter.State.Loading

    Column(modifier = modifier) {
        Card(
            elevation = 3.dp,
            modifier = Modifier
                .placeholder(visible = isImgLoading, color = Color.Gray)
                .clickable { onItemClicked(recipeData) }
                .placeholder(
                    visible = isImgLoading,
                    color = Color.Gray,
                    highlight = PlaceholderHighlight.shimmer(
                        highlightColor = Color.White,
                    )
                ),
            shape = RoundedCornerShape(10.dp)
        ) {

            Image(
                painter = painter,
                // painter = painterResource(id = R.drawable.salad),
                contentDescription = recipeData.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(4f / 3f)
            )


        }

        Row(modifier = Modifier.padding(top = 5.dp)) {
            Text(
                text = recipeData.title ?: "",
                color = Color.Black,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp,
                textAlign = TextAlign.Start,
                maxLines = 2,
                modifier = Modifier.weight(2f)

            )

            Text(
                text = """${recipeData.readyInMinutes.toString()} mins""",
                color = Color.Black.copy(alpha = 0.5F),
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f),
                maxLines = 1

            )

        }

    }
}


@Composable
fun CustomStaggeredVerticalGrid(
    modifier: Modifier = Modifier,
    numColumns: Int = 2,
    content: @Composable () -> Unit
) {
    // inside this grid we are creating
    // a layout on below line.
    Layout(
        content = content,
        modifier = modifier
    ) { measurable, constraints ->
        val columnWidth = (constraints.maxWidth / numColumns)

        // creating and initializing our items constraint widget.
        val itemConstraints = constraints.copy(maxWidth = columnWidth)

        // creating and initializing our column height
        val columnHeights = IntArray(numColumns) { 0 }

        val placeable = measurable.map { measurable ->
            val column = testColumn(columnHeights)
            val placeable = measurable.measure(itemConstraints)

            columnHeights[column] += placeable.height
            placeable
        }

        //  creating a variable for
        // our height and specifying height for it.
        val height =
            columnHeights.maxOrNull()?.coerceIn(constraints.minHeight, constraints.maxHeight)
                ?: constraints.minHeight

        layout(
            width = constraints.maxWidth,
            height = height
        ) {
            val columnYPointers = IntArray(numColumns) { 0 }

            // setting x and y for each placeable item
            placeable.forEach { placeable ->
                // calling test
                // column method to get our column index
                val column = testColumn(columnYPointers)

                placeable.place(
                    x = columnWidth * column,
                    y = columnYPointers[column]
                )

                // setting column y pointer and incrementing it.
                columnYPointers[column] += placeable.height
            }
        }
    }
}

// test column method for setting height.
private fun testColumn(columnHeights: IntArray): Int {
    var minHeight = Int.MAX_VALUE

    var columnIndex = 0

    //setting column  height for each index.
    columnHeights.forEachIndexed { index, height ->
        if (height < minHeight) {
            minHeight = height
            columnIndex = index
        }
    }

    return columnIndex
}


@Preview("default")
//@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun RecipesGridPreview() {
    RecipesAppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            CategoryBar(categories = getAllRecipeCategories(), onSelectedChanged = {})
            RecipesGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                recipes = listOf(
                    RecipeData(title = "Spicy Salad with Kidney Beans", readyInMinutes = 25),
                    RecipeData(title = "Spicy Salad with Kidney Beans", readyInMinutes = 25),
                    RecipeData(title = "Spicy Salad with Kidney Beans", readyInMinutes = 25),
                    RecipeData(title = "Spicy Salad with Kidney Beans", readyInMinutes = 25),
                    RecipeData(title = "Spicy Salad with Kidney Beans", readyInMinutes = 25)
                )
            ) {}
        }

    }
}
