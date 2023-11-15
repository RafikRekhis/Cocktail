package fr.enseirb.gl.cocktail.models

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class RecentViewedCocktail(
    val idDrink: String,
    val strDrinkThumb: String
) {
    companion object {
        fun fromJsonList(json: String): MutableList<RecentViewedCocktail> {
            val listType = object : TypeToken<ArrayList<RecentViewedCocktail>>() {}.type
            return Gson().fromJson(json, listType)
        }

        fun toJsonList(list: List<RecentViewedCocktail>): String {
            return Gson().toJson(list)
        }
    }
}
