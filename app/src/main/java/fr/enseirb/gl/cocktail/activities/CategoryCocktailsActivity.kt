package fr.enseirb.gl.cocktail.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import fr.enseirb.gl.cocktail.adapters.CategoryCocktailsAdapter
import fr.enseirb.gl.cocktail.databinding.ActivityCategoryCocktailsBinding
import fr.enseirb.gl.cocktail.fragments.HomeFragment
import fr.enseirb.gl.cocktail.mvvm.CategoryCocktailsViewModel

class CategoryCocktailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryCocktailsBinding
    private lateinit var categoryCocktailsViewModel: CategoryCocktailsViewModel
    private lateinit var categoryCocktailsAdapter: CategoryCocktailsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryCocktailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.getStringExtra(HomeFragment.FILTER_NAME) != null) {
            binding.toolbarCategory.title = intent.getStringExtra(HomeFragment.FILTER_NAME)
        }

        //show back icon and return to previous activity
        setSupportActionBar(binding.toolbarCategory)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarCategory.setNavigationOnClickListener {
            onBackPressed()
        }

        prepareRecyclerView()

        categoryCocktailsViewModel = CategoryCocktailsViewModel()
        categoryCocktailsViewModel.getCategoryCocktails(intent.getStringExtra(HomeFragment.FILTER_NAME)!!)
        categoryCocktailsViewModel.observeCategoryCocktails().observe(this) { categoryCocktails ->
            binding.tvCategoryCount.text = "${categoryCocktails.size} cocktails"
            categoryCocktailsAdapter.setCocktails(categoryCocktails)
        }
    }

    private fun prepareRecyclerView() {
        categoryCocktailsAdapter = CategoryCocktailsAdapter()
        binding.rvCategoryCocktails.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = categoryCocktailsAdapter
        }
    }
}