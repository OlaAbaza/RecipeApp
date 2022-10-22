package com.example.recipesapp.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recipesapp.data.network.Length
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class RecipeData(
    val vegetarian: Boolean? = null,
    val glutenFree: Boolean? = null,
    val vegan: Boolean? = null,
    val aggregateLikes: Int? = null,
    val ingredients: List<Ingredient>? = null,
    val title: String? = null,
    @PrimaryKey val id: Int? = null,
    val image: String? = null,
    val readyInMinutes: Int? = null,
    val summary: String? = null,
    var isFavorite: Boolean = false
    // val steps: List<Steps?>? = null
) : Parcelable

@Parcelize
data class Ingredient(
    val amount: Double?,
    val id: Int?,
    val name: String?,
    val original: String?,
    val originalName: String?,
    val unit: String?
) : Parcelable

@Parcelize
data class Steps(
    val length: Length?,
    val number: Int?,
    val step: String?
) : Parcelable