package fr.enseirb.gl.cocktail.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import fr.enseirb.gl.cocktail.adapters.AlcoholCocktailsAdapter
import fr.enseirb.gl.cocktail.databinding.ActivityAlcoholCocktailsBinding
import fr.enseirb.gl.cocktail.fragments.HomeFragment
import fr.enseirb.gl.cocktail.mvvm.AlcoholCocktailsViewModel

class AlcoholCocktailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlcoholCocktailsBinding
    private lateinit var alcoholCocktailsViewModel: AlcoholCocktailsViewModel
    private lateinit var alcoholCocktailsAdapter: AlcoholCocktailsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlcoholCocktailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareRecyclerView()

        alcoholCocktailsViewModel = AlcoholCocktailsViewModel()

        alcoholCocktailsViewModel.getAlcoholCocktails(intent.getStringExtra(HomeFragment.FILTER_NAME)!!)
        alcoholCocktailsViewModel.observeAlcoholCocktails().observe(this) { alcoholCocktails ->
            binding.tvAlcoholCount.text = "${alcoholCocktails.size} cocktails"
            alcoholCocktailsAdapter.setCocktails(alcoholCocktails)
        }
    }

    private fun prepareRecyclerView() {
        alcoholCocktailsAdapter = AlcoholCocktailsAdapter()
        binding.rvAlcoholCocktails.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = alcoholCocktailsAdapter
        }
    }
}