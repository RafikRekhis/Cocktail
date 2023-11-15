package fr.enseirb.gl.cocktail.services

import fr.enseirb.gl.cocktail.models.CategoryList
import fr.enseirb.gl.cocktail.models.CocktailByFilterList
import fr.enseirb.gl.cocktail.models.CocktailList
import fr.enseirb.gl.cocktail.models.GlassList
import fr.enseirb.gl.cocktail.models.IngredientList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailApi {

    @GET("random.php")
    fun getRandomCocktail(): Call<CocktailList>

    @GET("lookup.php")
    fun getCocktailDetails(@Query("i") id: String): Call<CocktailList>

    @GET("list.php?c=list")
    fun getCocktailCategories(): Call<CategoryList>

    @GET("list.php?i=list")
    fun getCocktailIngredients(): Call<IngredientList>

    @GET("list.php?g=list")
    fun getCocktailGlass(): Call<GlassList>

    @GET("filter.php")
    fun getCocktailsByCategory(@Query("c") categoryName: String): Call<CocktailByFilterList>

    @GET("filter.php")
    fun getCocktailsByIngredient(@Query("i") ingredientName: String): Call<CocktailByFilterList>

    @GET("filter.php")
    fun getCocktailsByGlass(@Query("g") ingredientName: String): Call<CocktailByFilterList>

    @GET("search.php")
    fun getCocktailsByName(@Query("s") cocktailName: String): Call<CocktailList>
}