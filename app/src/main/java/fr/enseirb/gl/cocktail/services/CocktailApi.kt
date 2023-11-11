package fr.enseirb.gl.cocktail.services

import fr.enseirb.gl.cocktail.models.CategoryList
import fr.enseirb.gl.cocktail.models.CocktailByCategoryList
import fr.enseirb.gl.cocktail.models.CocktailList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailApi {

    @GET("random.php")
    fun getRandomCocktail(): Call<CocktailList>

    @GET("lookup.php")
    fun getCocktailDetails(@Query("i") id:String): Call<CocktailList>

    @GET("list.php?c=list")
    fun getCocktailCategories(): Call<CategoryList>

    @GET("filter.php")
    fun getCocktailsByCategory(@Query("c") categoryName: String): Call<CocktailByCategoryList>

    @GET("search.php")
    fun getCocktailsByName(@Query("s") cocktailName: String): Call<CocktailList>
}