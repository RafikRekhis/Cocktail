package fr.enseirb.gl.cocktail.services

import fr.enseirb.gl.cocktail.models.CocktailList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailApi {

    @GET("random.php")
    fun getRandomCocktail(): Call<CocktailList>

    @GET("lookup.php")
    fun getCocktailDetails(@Query("i") id:String): Call<CocktailList>
}