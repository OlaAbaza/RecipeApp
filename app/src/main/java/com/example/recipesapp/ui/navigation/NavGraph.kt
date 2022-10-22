package com.example.recipesapp.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.recipesapp.data.RECIPE_DATA_KEY
import com.example.recipesapp.domain.model.RecipeData
import com.example.recipesapp.ui.details.DetailsScreen
import com.example.recipesapp.ui.home.favorite.FavoriteScreen
import com.example.recipesapp.ui.home.HomeScreen
import com.example.recipesapp.ui.home.HomeTabs
import com.example.recipesapp.ui.home.search.SearchScreen
import com.example.recipesapp.ui.onboarding.OnBoardingScreen
import com.example.recipesapp.ui.onboarding.OnBoardingViewModel
import com.example.recipesapp.ui.onboarding.onBoardingPages

@Composable
fun SetupNavGraph(
    modifier: Modifier = Modifier,
    finishActivity: () -> Unit = {},
    navController: NavHostController = rememberNavController(),
    startDestination: String = MainDestinations.Home.route,
    viewModel: OnBoardingViewModel = hiltViewModel()
) {
    val onBoardingComplete = remember {
        mutableStateOf(viewModel.isOnBoardingComplete)
    }

    val actions = remember(navController) { MainActions(navController) }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = MainDestinations.OnBoarding.route) {
            // Intercept back in On boarding: make it finish the activity
            BackHandler {
                finishActivity()
            }

            OnBoardingScreen(
                pages = onBoardingPages,
                onFinishOnBoarding = {
                    // Set the flag so that on boarding is not shown next time.
                    onBoardingComplete.value = true
                    viewModel.saveOnBoardingState(true)
                    actions.onBoardingComplete()
                }
            )
        }

        navigation(
            route = MainDestinations.Home.route,
            startDestination = HomeTabs.FEATURED.route
        ) {
            home(
                onRecipeSelected = actions.openRecipeDetails,
                onBoardingComplete = onBoardingComplete,
                navController = navController,
                modifier = modifier
            )
        }
        composable(
            route = MainDestinations.Details.route
        ) { backStackEntry: NavBackStackEntry ->
            val recipeData =
                navController.previousBackStackEntry?.savedStateHandle?.get<RecipeData>(
                    RECIPE_DATA_KEY
                )

            recipeData?.let {
                DetailsScreen(
                    upPress = { actions.upPress(backStackEntry) },
                    recipeData = it
                )
            }

        }

    }
}


class MainActions(navController: NavController) {

    val onBoardingComplete: () -> Unit = {
        navController.popBackStack()
    }

    val openRecipeDetails = { recipeData: RecipeData, from: NavBackStackEntry ->
        // In order to discard duplicated navigation events, we check the Lifecycle
        if (from.lifecycleIsResumed()) {
            navController.currentBackStackEntry?.savedStateHandle?.set(
                RECIPE_DATA_KEY,
                recipeData
            )
            navController.navigate(MainDestinations.Details.route)
        }
    }


    // Used from details
    val upPress: (from: NavBackStackEntry) -> Unit = { from ->
        // In order to discard duplicated navigation events, we check the Lifecycle
        if (from.lifecycleIsResumed()) {
            navController.navigateUp()
        }
    }
}

fun NavGraphBuilder.home(
    onRecipeSelected: (RecipeData, NavBackStackEntry) -> Unit,
    onBoardingComplete: MutableState<Boolean>,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    composable(HomeTabs.FEATURED.route) { from ->
        // Show on boarding instead if not shown yet.
        LaunchedEffect(onBoardingComplete) {
            if (!onBoardingComplete.value) {
                navController.navigate(MainDestinations.OnBoarding.route)
            }
        }
        if (onBoardingComplete.value) { // Avoid glitch when showing on boarding
            HomeScreen(
                onSelectRecipe = { recipeData -> onRecipeSelected(recipeData, from) },
                modifier = modifier
            )
        }
    }

    composable(route = HomeTabs.SEARCH.route) { from ->
        SearchScreen(onSelectRecipe = { recipeData -> onRecipeSelected(recipeData, from) })
    }

    composable(route = HomeTabs.FAVORITE.route) { from ->
        FavoriteScreen(
            onSelectRecipe = { recipeData -> onRecipeSelected(recipeData, from) },
        )
    }


}

/**
 * If the lifecycle is not resumed it means this NavBackStackEntry already processed a nav event.
 *
 * This is used to de-duplicate navigation events.
 */
private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED
