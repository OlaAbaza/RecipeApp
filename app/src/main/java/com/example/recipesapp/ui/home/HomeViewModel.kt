package com.example.recipesapp.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipesapp.domain.repository.RecipeRepository
import com.example.recipesapp.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val recipeRepository: RecipeRepository) :
    ViewModel() {

    var state by mutableStateOf<HomeViewState>(HomeViewState.Loading)
        private set

    var selectedCategory by mutableStateOf(RecipeCategory.BEVERAGE)
        private set

    private var fetchJob: Job? = null


    init {
        loadRecipesDataByCategory(9, listOf(RecipeCategory.APPETIZER.value))
    }

    private fun loadRecipesDataByCategory(
        number: Int = 6,
        category: List<String>
    ) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            state = HomeViewState.Loading

            state = when (val result = recipeRepository.getRandomRecipesDate(number, category)) {
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

    fun onEvent(event: HomeViewEvent) {
        when (event) {
            is HomeViewEvent.ChangeCategory -> {
                selectedCategory = event.selectedCategory
                loadRecipesDataByCategory(category = listOf(event.selectedCategory.value))
            }
        }
    }
}