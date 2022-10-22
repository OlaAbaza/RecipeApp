package com.example.recipesapp.ui.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.recipesapp.R

enum class HomeTabs(var route: String, @DrawableRes val icon: Int, @StringRes var title: Int) {
    FEATURED(HomeDestinations.FEATURED_ROUTE, R.drawable.ic_home, R.string.featured),
    SEARCH(HomeDestinations.SEARCH_ROUTE, R.drawable.ic_search, R.string.search),
    FAVORITE(HomeDestinations.FAVORITE_ROUTE, R.drawable.ic_heart, R.string.favorite)
}

private object HomeDestinations {
    const val FEATURED_ROUTE = "featured"
    const val SEARCH_ROUTE = "search"
    const val FAVORITE_ROUTE = "favorite"
}
