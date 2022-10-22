package com.example.recipesapp.ui.home.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipesapp.domain.repository.RecipeRepository
import com.example.recipesapp.domain.util.Resource
import com.example.recipesapp.ui.home.HomeViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    var query by mutableStateOf("")
        private set

    var state by mutableStateOf<HomeViewState>(HomeViewState.Empty)
        private set

    private var getSearchResultJob: Job? = null


    fun getSearchResult(number: Int = 6) {
        getSearchResultJob?.cancel()

        getSearchResultJob = viewModelScope.launch {
            state = HomeViewState.Loading

            state =
                when (val result = recipeRepository.getRandomRecipesDate(number, listOf(query))) {
                    is Resource.Success -> {
                        if (result.data.isNullOrEmpty())
                            HomeViewState.Empty
                        else
                            HomeViewState.Success(data = result.data)

                    }
                    is Resource.Error -> {
                        HomeViewState.Error(message = result.message)
                    }
                }
        }
    }

    fun updateSearchQuery(query: String) {
        this.query = query
    }
}