package fr.enseirb.gl.cocktail.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.enseirb.gl.cocktail.models.CocktailByFilter
import fr.enseirb.gl.cocktail.models.CocktailByFilterList
import fr.enseirb.gl.cocktail.services.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IngredientCocktailsViewModel : ViewModel() {
    private var ingredientCocktailsLiveData = MutableLiveData<List<CocktailByFilter>>()

    fun getIngredientCocktails(ingredientName: String) {
        RetrofitInstance.api.getCocktailsByIngredient(ingredientName)
            .enqueue(object : Callback<CocktailByFilterList> {
                override fun onResponse(
                    call: Call<CocktailByFilterList>,
                    response: Response<CocktailByFilterList>
                ) {
                    response.body()?.let { cocktailList ->
                        ingredientCocktailsLiveData.postValue(cocktailList.drinks)
                    }
                }

                override fun onFailure(call: Call<CocktailByFilterList>, t: Throwable) {
                    android.util.Log.d(
                        "IngredientCocktailsFragment",
                        "onFailure: ${t.message.toString()}"
                    )
                }
            })
    }

    fun observeIngredientCocktails(): MutableLiveData<List<CocktailByFilter>> {
        return ingredientCocktailsLiveData
    }
}