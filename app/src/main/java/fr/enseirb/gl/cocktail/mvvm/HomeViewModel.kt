package fr.enseirb.gl.cocktail.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.enseirb.gl.cocktail.models.Category
import fr.enseirb.gl.cocktail.models.CategoryList
import fr.enseirb.gl.cocktail.models.CocktailList
import fr.enseirb.gl.cocktail.models.Drink
import fr.enseirb.gl.cocktail.services.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel() : ViewModel() {
    private var randomCocktailLiveData = MutableLiveData<Drink>()
    private var categoriesLiveData = MutableLiveData<List<Category>>()
    private var searchedCocktailsLiveData = MutableLiveData<List<Drink>>()

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
                response.body()?.let {categoryList ->
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
        RetrofitInstance.api.getCocktailsByName(cocktailName).enqueue(object : Callback<CocktailList> {
            override fun onResponse(
                call: Call<CocktailList>,
                response: Response<CocktailList>
            ) {
                response.body()?.let {cocktailList ->
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
}