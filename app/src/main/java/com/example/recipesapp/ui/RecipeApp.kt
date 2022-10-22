package com.example.recipesapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.recipesapp.ui.home.HomeTabs
import com.example.recipesapp.ui.navigation.SetupNavGraph
import com.example.recipesapp.ui.theme.LightGreen
import com.example.recipesapp.ui.theme.RecipesAppTheme

@Composable
fun RecipeApp(finishActivity: () -> Unit) {

    RecipesAppTheme {
        val tabs = remember { HomeTabs.values() }
        val navController = rememberNavController()
        Scaffold(
            backgroundColor = Color.White,
            bottomBar = {
                RecipeBottomBar(
                    navController = navController,
                    tabs = tabs
                )
            }
        ) { innerPaddingModifier ->
            SetupNavGraph(
                modifier = Modifier.padding(innerPaddingModifier),
                finishActivity = finishActivity,
                navController = navController
            )
        }
    }

}


@Composable
fun RecipeBottomBar(
    navController: NavController,
    tabs: Array<HomeTabs>
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
        ?: HomeTabs.FEATURED.route

    val routes = remember { HomeTabs.values().map { it.route } }

    if (currentRoute in routes) {
        BottomNavigation(
            modifier = Modifier.windowInsetsBottomHeight(
                WindowInsets.navigationBars.add(WindowInsets(bottom = 56.dp))
            ),
            backgroundColor = Color.Transparent,
            elevation = 15.dp
        ) {
            tabs.forEach { tab ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painterResource(id = tab.icon),
                            contentDescription = stringResource(tab.title),
                            modifier = Modifier.size(35.dp)
                        )
                    },
                    selectedContentColor = LightGreen,
                    unselectedContentColor = Color.LightGray,
                    selected = currentRoute == tab.route,
                    onClick = {
                        if (tab.route != currentRoute) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            navController.navigate(tab.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // re selecting the same item
                                launchSingleTop = true
                                // Restore state when re selecting a previously selected item
                                restoreState = true
                            }
                        }
                    },
                    modifier = Modifier
                        .navigationBarsPadding()
                        .background(Color.White)

                )
            }
        }
    }
}