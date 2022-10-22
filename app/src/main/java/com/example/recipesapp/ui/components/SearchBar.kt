package com.example.recipesapp.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.recipesapp.ui.theme.RecipesAppTheme
import com.example.recipesapp.ui.theme.spacing

@OptIn(ExperimentalComposeUiApi::class)
@Composable

fun SearchBar(
    modifier: Modifier = Modifier, query: String,
    onQueryChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit

) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(modifier = modifier.fillMaxWidth()) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(0.99f)
                .padding(MaterialTheme.spacing.small)
                .background(Color.Transparent),
            value = query,
            onValueChange = {
                onQueryChanged(it)
            },
            label = { Text(text = "Search") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onExecuteSearch()
                    keyboardController?.hide()
                },
            ),
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "") },
            textStyle = TextStyle(color = MaterialTheme.colors.onSurface),

            )
    }

}

@Preview("default")
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SearchBarPreview() {
    RecipesAppTheme {
        Surface(modifier = Modifier.fillMaxWidth()) {
            SearchBar(
                query = "",
                onQueryChanged = {},
                onExecuteSearch = {}
            )
        }
    }
}
