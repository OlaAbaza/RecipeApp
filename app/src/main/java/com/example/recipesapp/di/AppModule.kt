package com.example.recipesapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.recipesapp.data.BASE_URL
import com.example.recipesapp.data.database.RecipeDatabase
import com.example.recipesapp.data.network.RecipeApi
import com.example.recipesapp.data.repository.DataStoreRepositoryImpl
import com.example.recipesapp.data.repository.FavoriteRecipeRepositoryImpl
import com.example.recipesapp.domain.repository.FavoriteRecipeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //addConverterFactory too automatically parse our data to dto classes

    @Provides
    @Singleton
    fun provideMovieApi(): RecipeApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideDataStoreRepository(
        @ApplicationContext context: Context
    ) = DataStoreRepositoryImpl(context = context)

    @Provides
    @Singleton
    fun provideRecipeDataBase(application: Application): RecipeDatabase {
        return Room.databaseBuilder(
            application,
            RecipeDatabase::class.java,
            RecipeDatabase.DATABASE_NAME
        ).build()

    }

    @Provides
    @Singleton
    fun provideFavoriteRecipeRepository(database: RecipeDatabase): FavoriteRecipeRepository {
        return FavoriteRecipeRepositoryImpl(database.recipeDao)

    }

}