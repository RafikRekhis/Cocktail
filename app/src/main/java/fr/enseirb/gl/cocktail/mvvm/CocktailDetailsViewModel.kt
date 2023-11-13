package fr.enseirb.gl.cocktail.mvvm

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.enseirb.gl.cocktail.models.CocktailList
import fr.enseirb.gl.cocktail.models.Drink
import fr.enseirb.gl.cocktail.models.SavedCocktail
import fr.enseirb.gl.cocktail.services.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CocktailDetailsViewModel(private val sharedPreferences: SharedPreferences) : ViewModel() {
    private var cocktailDetailsLiveData = MutableLiveData<Drink>()

    fun getCocktailDetails(id:String) {
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

    fun addCocktailToFavorite(cocktail: SavedCocktail, maxFavorites: Int) {

        // Récupérer la liste actuelle des cocktails favoris depuis SharedPreferences
        val favoritesJson = sharedPreferences.getString("favorites", null)
        val favoritesList: MutableList<SavedCocktail> = if (favoritesJson != null) {
            SavedCocktail.fromJsonList(favoritesJson)
        } else {
            mutableListOf()
        }

        // Ajouter le nouveau cocktail au début de la liste
        favoritesList.add(0, cocktail)

        // Limiter le nombre de favoris à maxFavorites
        if (favoritesList.size > maxFavorites) {
            // Supprimer le dernier élément s'il y en a trop
            favoritesList.removeAt(favoritesList.size - 1)
        }

        // Convertir la liste en format JSON
        val newFavoritesJson = SavedCocktail.toJsonList(favoritesList)

        // Enregistrer la nouvelle liste dans SharedPreferences
        sharedPreferences.edit().putString("favorites", newFavoritesJson).apply()
    }


}