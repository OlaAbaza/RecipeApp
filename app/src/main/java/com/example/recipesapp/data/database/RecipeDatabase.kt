package com.example.recipesapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.recipesapp.domain.model.RecipeData

@Database(
    entities = [RecipeData::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DataConverter::class)
abstract class RecipeDatabase : RoomDatabase() {

    abstract val recipeDao: RecipeDao

    companion object {
        const val DATABASE_NAME = "recipe_db"
    }
}