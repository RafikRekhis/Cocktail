package fr.enseirb.gl.cocktail.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.enseirb.gl.cocktail.models.CocktailByFilter
import fr.enseirb.gl.cocktail.models.CocktailByFilterList
import fr.enseirb.gl.cocktail.services.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlcoholCocktailsViewModel : ViewModel() {
    private var alcoholCocktailsLiveData = MutableLiveData<List<CocktailByFilter>>()

    fun getAlcoholCocktails(alcoholName: String) {
        RetrofitInstance.api.getCocktailsByAlcohol(alcoholName)
            .enqueue(object : Callback<CocktailByFilterList> {
                override fun onResponse(
                    call: Call<CocktailByFilterList>,
                    response: Response<CocktailByFilterList>
                ) {
                    response.body()?.let { cocktailList ->
                        alcoholCocktailsLiveData.postValue(cocktailList.drinks)
                    }
                }

                override fun onFailure(call: Call<CocktailByFilterList>, t: Throwable) {
                    android.util.Log.d(
                        "AlcoholCocktailsFragment",
                        "onFailure: ${t.message.toString()}"
                    )
                }
            })
    }

    fun observeAlcoholCocktails(): MutableLiveData<List<CocktailByFilter>> {
        return alcoholCocktailsLiveData
    }
}