package fr.enseirb.gl.cocktail.models

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class SavedCocktail(
    val idDrink: String,
    val strDrink: String,
    val strDrinkThumb: String
) {
    companion object {
        fun fromJsonList(json: String): MutableList<SavedCocktail> {
            val listType = object : TypeToken<ArrayList<SavedCocktail>>() {}.type
            return Gson().fromJson(json, listType)
        }

        fun toJsonList(list: List<SavedCocktail>): String {
            return Gson().toJson(list)
        }
    }
}
