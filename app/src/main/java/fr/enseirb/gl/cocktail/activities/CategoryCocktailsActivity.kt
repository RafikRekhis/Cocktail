package fr.enseirb.gl.cocktail.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import fr.enseirb.gl.cocktail.databinding.ActivityCategoryCocktailsBinding
import fr.enseirb.gl.cocktail.fragments.HomeFragment
import fr.enseirb.gl.cocktail.mvvm.CategoryCocktailsViewModel

class CategoryCocktailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryCocktailsBinding
    private lateinit var categoryCocktailsViewModel: CategoryCocktailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryCocktailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoryCocktailsViewModel = CategoryCocktailsViewModel()

        categoryCocktailsViewModel.getCategoryCocktails(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)
        categoryCocktailsViewModel.observeCategoryCocktails().observe(this) { categoryCocktails ->
            categoryCocktails.forEach {
                Log.d("test", it.strDrink)
            }
        }

    }
}