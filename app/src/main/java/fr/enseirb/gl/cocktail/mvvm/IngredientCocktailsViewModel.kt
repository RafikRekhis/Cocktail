package fr.enseirb.gl.cocktail.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.enseirb.gl.cocktail.models.CocktailByCategory
import fr.enseirb.gl.cocktail.models.CocktailByCategoryList
import fr.enseirb.gl.cocktail.services.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IngredientCocktailsViewModel : ViewModel() {
    private var ingredientCocktailsLiveData = MutableLiveData<List<CocktailByCategory>>()

    fun getIngredientCocktails(ingredientName: String) {
        RetrofitInstance.api.getCocktailsByIngredient(ingredientName)
            .enqueue(object : Callback<CocktailByCategoryList> {
                override fun onResponse(
                    call: Call<CocktailByCategoryList>,
                    response: Response<CocktailByCategoryList>
                ) {
                    response.body()?.let { cocktailList ->
                        ingredientCocktailsLiveData.postValue(cocktailList.drinks)
                    }
                }

                override fun onFailure(call: Call<CocktailByCategoryList>, t: Throwable) {
                    android.util.Log.d(
                        "IngredientCocktailsFragment",
                        "onFailure: ${t.message.toString()}"
                    )
                }
            })
    }

    fun observeIngredientCocktails(): MutableLiveData<List<CocktailByCategory>> {
        return ingredientCocktailsLiveData
    }
}