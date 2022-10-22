package com.example.recipesapp.ui.onboarding

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipesapp.domain.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val repository: DataStoreRepository
) : ViewModel() {

    var isOnBoardingComplete by mutableStateOf(false)
        private set

    init {
        getOnBoardingState()
    }

    fun saveOnBoardingState(completed: Boolean) {
        viewModelScope.launch() {
            repository.saveOnBoardingState(completed = completed)
        }
    }

    private fun getOnBoardingState() {
        viewModelScope.launch {
            repository.readOnBoardingState().collect { isCompleted ->
                isOnBoardingComplete = isCompleted
            }
        }
    }
}