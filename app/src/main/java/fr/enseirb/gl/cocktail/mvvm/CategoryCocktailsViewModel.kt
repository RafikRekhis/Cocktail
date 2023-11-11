package fr.enseirb.gl.cocktail.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.enseirb.gl.cocktail.models.CocktailByCategory
import fr.enseirb.gl.cocktail.models.CocktailByCategoryList
import fr.enseirb.gl.cocktail.services.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryCocktailsViewModel : ViewModel() {
    private var categoryCocktailsLiveData = MutableLiveData<List<CocktailByCategory>>()

    fun getCategoryCocktails(categoryName: String) {
        RetrofitInstance.api.getCocktailsByCategory(categoryName)
            .enqueue(object : Callback<CocktailByCategoryList> {
                override fun onResponse(
                    call: Call<CocktailByCategoryList>,
                    response: Response<CocktailByCategoryList>
                ) {
                    response.body()?.let { cocktailList ->
                        categoryCocktailsLiveData.postValue(cocktailList.drinks)
                    }
                }

                override fun onFailure(call: Call<CocktailByCategoryList>, t: Throwable) {
                    android.util.Log.d(
                        "CategoryCocktailsFragment",
                        "onFailure: ${t.message.toString()}"
                    )
                }
            })
    }

    fun observeCategoryCocktails(): MutableLiveData<List<CocktailByCategory>> {
        return categoryCocktailsLiveData
    }
}