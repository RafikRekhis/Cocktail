package fr.enseirb.gl.cocktail.services

import fr.enseirb.gl.cocktail.models.CocktailList
import retrofit2.Call
import retrofit2.http.GET

interface CocktailApi {

    @GET("random.php")
    fun getRandomCocktail(): Call<CocktailList>
}