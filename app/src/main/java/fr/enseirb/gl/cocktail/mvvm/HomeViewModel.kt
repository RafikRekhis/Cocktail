package fr.enseirb.gl.cocktail.mvvm

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.enseirb.gl.cocktail.models.Category
import fr.enseirb.gl.cocktail.models.CategoryList
import fr.enseirb.gl.cocktail.models.CocktailList
import fr.enseirb.gl.cocktail.models.Drink
import fr.enseirb.gl.cocktail.models.Glass
import fr.enseirb.gl.cocktail.models.GlassList
import fr.enseirb.gl.cocktail.models.SavedCocktail
import fr.enseirb.gl.cocktail.models.Ingredient
import fr.enseirb.gl.cocktail.models.IngredientList
import fr.enseirb.gl.cocktail.models.RecentViewedCocktail
import fr.enseirb.gl.cocktail.services.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val sharedPreferences: SharedPreferences) : ViewModel() {
    private var randomCocktailLiveData = MutableLiveData<Drink>()
    private var categoriesLiveData = MutableLiveData<List<Category>>()
    private var searchedCocktailsLiveData = MutableLiveData<List<Drink>>()
    private var ingredientsLiveData = MutableLiveData<List<Ingredient>>()
    private var glassLiveData = MutableLiveData<List<Glass>>()

    private var favoritesLiveData = MutableLiveData<List<SavedCocktail>>()
    private var recentViewedCocktailsLiveData = MutableLiveData<List<RecentViewedCocktail>>()


    fun getFavorites() {
        //obtenir la liste des cocktails favoris depuis SharedPreferences
        val favoritesJson = sharedPreferences.getString("favorites", null)
        val favoritesList: MutableList<SavedCocktail> = if (favoritesJson != null) {
            SavedCocktail.fromJsonList(favoritesJson)
        } else {
            mutableListOf()
        }
        favoritesLiveData.postValue(favoritesList)
    }

    fun observeFavorites(): LiveData<List<SavedCocktail>> {

        return favoritesLiveData
    }

    fun getRecentViewedCocktails() {
        //obtenir la liste des cocktails récemment vus depuis SharedPreferences
        val recentViewedCocktailsJson = sharedPreferences.getString("recentViewedCocktails", null)
        val recentViewedCocktailsList: MutableList<RecentViewedCocktail> =
            if (recentViewedCocktailsJson != null) {
                RecentViewedCocktail.fromJsonList(recentViewedCocktailsJson)
            } else {
                mutableListOf()
            }
        recentViewedCocktailsLiveData.postValue(recentViewedCocktailsList)
    }

    fun observeRecentViewedCocktails(): LiveData<List<RecentViewedCocktail>> {
        return recentViewedCocktailsLiveData
    }


    fun getRandomCocktail() {
        RetrofitInstance.api.getRandomCocktail().enqueue(object : Callback<CocktailList> {
            override fun onResponse(
                call: Call<CocktailList>,
                response: Response<CocktailList>
            ) {
                if (response.body() != null) {
                    val randomCocktail: Drink = response.body()!!.drinks[0]
                    randomCocktailLiveData.value = randomCocktail
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<CocktailList>, t: Throwable) {
                Log.d("HomeFragment", "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun observeRandomCocktail(): LiveData<Drink> {
        return randomCocktailLiveData
    }

    fun getCategories() {
        RetrofitInstance.api.getCocktailCategories().enqueue(object : Callback<CategoryList> {
            override fun onResponse(
                call: Call<CategoryList>,
                response: Response<CategoryList>
            ) {
                response.body()?.let { categoryList ->
                    categoriesLiveData.postValue(categoryList.drinks)
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("HomeFragment", "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun observeCategories(): LiveData<List<Category>> {
        return categoriesLiveData
    }

    fun searchCocktailsByName(cocktailName: String) {
        RetrofitInstance.api.getCocktailsByName(cocktailName)
            .enqueue(object : Callback<CocktailList> {
                override fun onResponse(
                    call: Call<CocktailList>,
                    response: Response<CocktailList>
                ) {
                    response.body()?.let { cocktailList ->
                        searchedCocktailsLiveData.postValue(cocktailList.drinks)
                    }
                }

                override fun onFailure(call: Call<CocktailList>, t: Throwable) {
                    Log.d("HomeFragment", "onFailure: ${t.message.toString()}")
                }
            })
    }

    fun observeSearchedCocktails(): LiveData<List<Drink>> {
        return searchedCocktailsLiveData
    }


    fun getIngredients() {
        RetrofitInstance.api.getCocktailIngredients().enqueue(object : Callback<IngredientList> {
            override fun onResponse(
                call: Call<IngredientList>,
                response: Response<IngredientList>
            ) {
                response.body()?.let { ingredientList ->
                    ingredientsLiveData.postValue(ingredientList.drinks)
                }
            }

            override fun onFailure(call: Call<IngredientList>, t: Throwable) {
                Log.d("HomeFragment", "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun observeIngredients(): LiveData<List<Ingredient>> {
        return ingredientsLiveData
    }

    fun getGlass() {
        RetrofitInstance.api.getCocktailGlass().enqueue(object : Callback<GlassList> {
            override fun onResponse(
                call: Call<GlassList>,
                response: Response<GlassList>
            ) {
                response.body()?.let { glassList ->
                    glassLiveData.postValue(glassList.drinks)
                }
            }

            override fun onFailure(call: Call<GlassList>, t: Throwable) {
                Log.d("HomeFragment", "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun observeGlass(): LiveData<List<Glass>> {
        return glassLiveData
    }
}