package fr.enseirb.gl.cocktail.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import fr.enseirb.gl.cocktail.adapters.IngredientCocktailsAdapter
import fr.enseirb.gl.cocktail.databinding.ActivityIngredientCocktailsBinding
import fr.enseirb.gl.cocktail.fragments.HomeFragment
import fr.enseirb.gl.cocktail.mvvm.IngredientCocktailsViewModel

class IngredientCocktailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIngredientCocktailsBinding
    private lateinit var categoryCocktailsViewModel: IngredientCocktailsViewModel
    private lateinit var categoryCocktailsAdapter: IngredientCocktailsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIngredientCocktailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareRecyclerView()

        categoryCocktailsViewModel = IngredientCocktailsViewModel()

        categoryCocktailsViewModel.getIngredientCocktails(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)
        categoryCocktailsViewModel.observeIngredientCocktails().observe(this) { categoryCocktails ->
            binding.tvIngredientCount.text = "${categoryCocktails.size} cocktails"
            categoryCocktailsAdapter.setCocktails(categoryCocktails)
        }
    }

    private fun prepareRecyclerView() {
        categoryCocktailsAdapter = IngredientCocktailsAdapter()
        binding.rvIngredientCocktails.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = categoryCocktailsAdapter
        }
    }
}