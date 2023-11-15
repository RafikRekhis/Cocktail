package fr.enseirb.gl.cocktail.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.enseirb.gl.cocktail.models.CocktailByFilter
import fr.enseirb.gl.cocktail.models.CocktailByFilterList
import fr.enseirb.gl.cocktail.services.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryCocktailsViewModel : ViewModel() {
    private var categoryCocktailsLiveData = MutableLiveData<List<CocktailByFilter>>()

    fun getCategoryCocktails(categoryName: String) {
        RetrofitInstance.api.getCocktailsByCategory(categoryName)
            .enqueue(object : Callback<CocktailByFilterList> {
                override fun onResponse(
                    call: Call<CocktailByFilterList>,
                    response: Response<CocktailByFilterList>
                ) {
                    response.body()?.let { cocktailList ->
                        categoryCocktailsLiveData.postValue(cocktailList.drinks)
                    }
                }

                override fun onFailure(call: Call<CocktailByFilterList>, t: Throwable) {
                    android.util.Log.d(
                        "CategoryCocktailsFragment",
                        "onFailure: ${t.message.toString()}"
                    )
                }
            })
    }

    fun observeCategoryCocktails(): MutableLiveData<List<CocktailByFilter>> {
        return categoryCocktailsLiveData
    }
}