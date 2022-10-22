package com.example.recipesapp.data.database

import androidx.room.TypeConverter
import com.example.recipesapp.domain.model.Ingredient
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.ParameterizedType


class DataConverter {

    private val moshi = Moshi.Builder().build()
    private val listMyData: ParameterizedType =
        Types.newParameterizedType(List::class.java, Ingredient::class.java)
    private val jsonAdapter: JsonAdapter<List<Ingredient>> = moshi.adapter(listMyData)

    @TypeConverter
    fun listIngredientToJsonStr(listIngredient: List<Ingredient>?): String? {
        return jsonAdapter.toJson(listIngredient)
    }

    @TypeConverter
    fun jsonStrToListIngredient(jsonStr: String?): List<Ingredient?>? {
        return jsonStr?.let { jsonAdapter.fromJson(jsonStr) }
    }
}