package com.example.recipesapp.di

import com.example.recipesapp.data.repository.DataStoreRepositoryImpl
import com.example.recipesapp.data.repository.RecipeRepositoryImpl
import com.example.recipesapp.domain.repository.DataStoreRepository
import com.example.recipesapp.domain.repository.RecipeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    //binds used with interfaces
    @Binds
    @Singleton
    abstract fun bindRecipeRepositoryModule(
        recipeRepositoryImpl: RecipeRepositoryImpl
    ): RecipeRepository

    @Binds
    @Singleton
    abstract fun bindDataStoreRepositoryModule(
        dataStoreRepositoryImpl: DataStoreRepositoryImpl
    ): DataStoreRepository
}