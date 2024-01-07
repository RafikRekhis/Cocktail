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
    private lateinit var ingredientCocktailsViewModel: IngredientCocktailsViewModel
    private lateinit var ingredientCocktailsAdapter: IngredientCocktailsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIngredientCocktailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.getStringExtra(HomeFragment.FILTER_NAME) != null) {
            binding.toolbarIngredient.title = "Cocktails made with ${intent.getStringExtra(HomeFragment.FILTER_NAME)}"
        }

        //show back icon and return to previous activity
        setSupportActionBar(binding.toolbarIngredient)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarIngredient.setNavigationOnClickListener {
            onBackPressed()
        }

        prepareRecyclerView()

        ingredientCocktailsViewModel = IngredientCocktailsViewModel()

        ingredientCocktailsViewModel.getIngredientCocktails(intent.getStringExtra(HomeFragment.FILTER_NAME)!!)
        ingredientCocktailsViewModel.observeIngredientCocktails().observe(this) { ingredientCocktails ->
            binding.tvIngredientCount.text = "${ingredientCocktails.size} cocktails"
            ingredientCocktailsAdapter.setCocktails(ingredientCocktails)
        }
    }

    private fun prepareRecyclerView() {
        ingredientCocktailsAdapter = IngredientCocktailsAdapter()
        binding.rvIngredientCocktails.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = ingredientCocktailsAdapter
        }
    }
}