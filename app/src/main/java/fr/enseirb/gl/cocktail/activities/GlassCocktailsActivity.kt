package fr.enseirb.gl.cocktail.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import fr.enseirb.gl.cocktail.adapters.GlassCocktailsAdapter
import fr.enseirb.gl.cocktail.databinding.ActivityGlassCocktailsBinding
import fr.enseirb.gl.cocktail.fragments.HomeFragment
import fr.enseirb.gl.cocktail.mvvm.GlassCocktailsViewModel

class GlassCocktailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGlassCocktailsBinding
    private lateinit var glassCocktailsViewModel: GlassCocktailsViewModel
    private lateinit var glassCocktailsAdapter: GlassCocktailsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGlassCocktailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.getStringExtra(HomeFragment.FILTER_NAME) != null) {
            binding.toolbarGlass.title = "Cocktails served in ${intent.getStringExtra(HomeFragment.FILTER_NAME)}"
        }

        //show back icon and return to previous activity
        setSupportActionBar(binding.toolbarGlass)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarGlass.setNavigationOnClickListener {
            onBackPressed()
        }

        prepareRecyclerView()

        glassCocktailsViewModel = GlassCocktailsViewModel()

        glassCocktailsViewModel.getGlassCocktails(intent.getStringExtra(HomeFragment.FILTER_NAME)!!)
        glassCocktailsViewModel.observeGlassCocktails().observe(this) { glassCocktails ->
            binding.tvGlassCount.text = "${glassCocktails.size} cocktails"
            glassCocktailsAdapter.setCocktails(glassCocktails)
        }
    }

    private fun prepareRecyclerView() {
        glassCocktailsAdapter = GlassCocktailsAdapter()
        binding.rvGlassCocktails.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = glassCocktailsAdapter
        }
    }
}