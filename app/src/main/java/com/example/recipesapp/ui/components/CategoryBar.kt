package com.example.recipesapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipesapp.ui.home.RecipeCategory
import com.example.recipesapp.ui.home.getAllRecipeCategories
import com.example.recipesapp.ui.home.getRecipeCategory
import com.example.recipesapp.ui.theme.Poppins
import com.example.recipesapp.ui.theme.spacing
import java.util.*

@Preview(showBackground = true)
@Composable
fun Chip(
    modifier: Modifier = Modifier,
    onSelectionChanged: (String) -> Unit = {},
    name: String = "chip",
    isSelected: Boolean = true,
    content: @Composable () -> Unit = {
        Text(
            text = name,
            color = Color.Black,
            modifier = Modifier.padding(MaterialTheme.spacing.small)
        )
    }
) {
    Surface(
        modifier = modifier
            .padding(MaterialTheme.spacing.extraSmall)
            .height(28.dp),
        elevation = 2.dp,
        shape = MaterialTheme.shapes.small,
        color = if (isSelected) MaterialTheme.colors.primary else Color.White
    ) {
        Row(modifier = Modifier
            .toggleable(
                value = isSelected,
                onValueChange = {
                    onSelectionChanged(name)
                }
            )
            .padding(start = MaterialTheme.spacing.extraSmall)
            .clip(MaterialTheme.shapes.small)
        ) {
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryBar(
    modifier: Modifier = Modifier,
    categories: List<RecipeCategory> = getAllRecipeCategories(),
    selectedCategory: RecipeCategory? = RecipeCategory.BEVERAGE,
    onSelectedChanged: (RecipeCategory) -> Unit = {},
) {
    Column(modifier = modifier.padding(MaterialTheme.spacing.extraSmall)) {
        LazyRow {
            items(categories) { item ->
                Chip(
                    name = item.value,
                    isSelected = selectedCategory == item,
                    onSelectionChanged = {
                        onSelectedChanged(getRecipeCategory(it))
                    },
                ) {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.value,
                        tint = if (selectedCategory == item) Color.White else Color.Black,
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.CenterHorizontally)
                    )

                    Text(
                        text = item.value.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(
                                Locale.ROOT
                            ) else it.toString()
                        },
                        style = MaterialTheme.typography.caption,
                        maxLines = 1,
                        color = if (selectedCategory == item) Color.White else Color.Black,
                        modifier = Modifier.padding(
                            horizontal = MaterialTheme.spacing.extraSmall,
                            vertical = MaterialTheme.spacing.extraSmall
                        ),
                        fontFamily = Poppins,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}
