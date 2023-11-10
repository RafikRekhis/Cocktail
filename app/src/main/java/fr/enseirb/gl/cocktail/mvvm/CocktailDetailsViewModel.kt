package fr.enseirb.gl.cocktail.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.enseirb.gl.cocktail.models.CocktailList
import fr.enseirb.gl.cocktail.models.Drink
import fr.enseirb.gl.cocktail.services.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CocktailDetailsViewModel() : ViewModel() {
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
}