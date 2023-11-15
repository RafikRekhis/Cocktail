package fr.enseirb.gl.cocktail.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.enseirb.gl.cocktail.models.CocktailByFilter
import fr.enseirb.gl.cocktail.models.CocktailByFilterList
import fr.enseirb.gl.cocktail.services.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GlassCocktailsViewModel : ViewModel() {
    private var glassCocktailsLiveData = MutableLiveData<List<CocktailByFilter>>()

    fun getGlassCocktails(glassName: String) {
        RetrofitInstance.api.getCocktailsByGlass(glassName)
            .enqueue(object : Callback<CocktailByFilterList> {
                override fun onResponse(
                    call: Call<CocktailByFilterList>,
                    response: Response<CocktailByFilterList>
                ) {
                    response.body()?.let { cocktailList ->
                        glassCocktailsLiveData.postValue(cocktailList.drinks)
                    }
                }

                override fun onFailure(call: Call<CocktailByFilterList>, t: Throwable) {
                    android.util.Log.d(
                        "GlassCocktailsFragment",
                        "onFailure: ${t.message.toString()}"
                    )
                }
            })
    }

    fun observeGlassCocktails(): MutableLiveData<List<CocktailByFilter>> {
        return glassCocktailsLiveData
    }
}