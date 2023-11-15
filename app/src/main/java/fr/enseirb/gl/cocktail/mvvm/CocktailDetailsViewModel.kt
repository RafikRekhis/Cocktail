package fr.enseirb.gl.cocktail.mvvm

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.enseirb.gl.cocktail.models.CocktailList
import fr.enseirb.gl.cocktail.models.Drink
import fr.enseirb.gl.cocktail.models.RecentViewedCocktail
import fr.enseirb.gl.cocktail.models.SavedCocktail
import fr.enseirb.gl.cocktail.services.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CocktailDetailsViewModel(private val sharedPreferences: SharedPreferences) : ViewModel() {
    private var cocktailDetailsLiveData = MutableLiveData<Drink>()

    fun getCocktailDetails(id: String) {
        RetrofitInstance.api.getCocktailDetails(id).enqueue(object : Callback<CocktailList> {
            override fun onResponse(
                call: Call<CocktailList>,
                response: Response<CocktailList>
            ) {
                if (response.body() != null) {
                    val cocktailDetails: Drink = response.body()!!.drinks[0]
                    cocktailDetailsLiveData.value = cocktailDetails
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<CocktailList>, t: Throwable) {
                Log.d("CocktailDetailsActivity", "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun observeCocktailDetails(): LiveData<Drink> {
        return cocktailDetailsLiveData
    }

    fun addCocktailToFavorite(cocktail: SavedCocktail) {

        // Récupérer la liste actuelle des cocktails favoris depuis SharedPreferences
        val favoritesJson = sharedPreferences.getString("favorites", null)
        val favoritesList: MutableList<SavedCocktail> = if (favoritesJson != null) {
            SavedCocktail.fromJsonList(favoritesJson)
        } else {
            mutableListOf()
        }

        // Vérifier si le cocktail existe déjà dans la liste
        val existingCocktail = favoritesList.find { it.idDrink == cocktail.idDrink }

        // Si le cocktail existe, le supprimer de la liste
        if (existingCocktail != null) {
            favoritesList.remove(existingCocktail)
        }

        // Ajouter le nouveau cocktail au début de la liste
        favoritesList.add(0, cocktail)

        // Convertir la liste en format JSON
        val newFavoritesJson = SavedCocktail.toJsonList(favoritesList)

        // Enregistrer la nouvelle liste dans SharedPreferences
        sharedPreferences.edit().putString("favorites", newFavoritesJson).apply()
    }

    fun removeFavorite(savedCocktail: SavedCocktail) {
        val favoritesJson = sharedPreferences.getString("favorites", null)
        val favoritesList: MutableList<SavedCocktail> = if (favoritesJson != null) {
            SavedCocktail.fromJsonList(favoritesJson)
        } else {
            mutableListOf()
        }
        favoritesList.remove(savedCocktail)
        val newFavoritesJson = SavedCocktail.toJsonList(favoritesList)
        sharedPreferences.edit().putString("favorites", newFavoritesJson).apply()
    }

    fun isFavorite(savedCocktail: SavedCocktail): Boolean {
        val favoritesJson = sharedPreferences.getString("favorites", null)
        val favoritesList: MutableList<SavedCocktail> = if (favoritesJson != null) {
            SavedCocktail.fromJsonList(favoritesJson)
        } else {
            mutableListOf()
        }
        favoritesList.forEach { cocktail ->
            if (cocktail.idDrink == savedCocktail.idDrink) {
                return true
            }
        }
        return false
    }

    fun addRecentViewedCocktail(recentViewedCocktail: RecentViewedCocktail) {
        // Récupérer la liste actuelle des cocktails récemment vus depuis SharedPreferences
        val recentViewedCocktailsJson = sharedPreferences.getString("recentViewedCocktails", null)
        val maxViewedCocktailsNumber = sharedPreferences.getInt("maxRecentItems", 5)
        val recentViewedCocktailsList: MutableList<RecentViewedCocktail> =
            if (recentViewedCocktailsJson != null) {
                RecentViewedCocktail.fromJsonList(recentViewedCocktailsJson)
            } else {
                mutableListOf()
            }

        // Vérifier si le cocktail existe déjà dans la liste
        val existingCocktail =
            recentViewedCocktailsList.find { it.idDrink == recentViewedCocktail.idDrink }

        // Si le cocktail existe, le supprimer de la liste
        if (existingCocktail != null) {
            recentViewedCocktailsList.remove(existingCocktail)
        }

        // Ajouter le nouveau cocktail au début de la liste
        recentViewedCocktailsList.add(0, recentViewedCocktail)

        // Si la liste contient plus de MAX_RECENT_ITEMS éléments, supprimer le dernier élément
        if (recentViewedCocktailsList.size > maxViewedCocktailsNumber) {
            recentViewedCocktailsList.removeAt(recentViewedCocktailsList.size - 1)
        }

        // Convertir la liste en format JSON
        val newRecentViewedCocktailsJson =
            RecentViewedCocktail.toJsonList(recentViewedCocktailsList)

        // Enregistrer la nouvelle liste dans SharedPreferences
        sharedPreferences.edit().putString("recentViewedCocktails", newRecentViewedCocktailsJson)
            .apply()
    }
}