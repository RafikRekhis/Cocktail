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

class HomeViewModel() : ViewModel() {
    private var randomCocktailLiveData = MutableLiveData<Drink>()

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
}