package fr.enseirb.gl.cocktail.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import fr.enseirb.gl.cocktail.databinding.ActivityCocktailDetailsBinding
import fr.enseirb.gl.cocktail.fragments.HomeFragment
import fr.enseirb.gl.cocktail.mvvm.CocktailDetailsViewModel

class CocktailDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCocktailDetailsBinding
    private lateinit var cocktailId: String
    private lateinit var cocktailName: String
    private lateinit var cocktailImage: String
    private lateinit var cocktailDetailsViewModel: CocktailDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCocktailDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cocktailDetailsViewModel = CocktailDetailsViewModel()

        getCocktailDetailsFromIntent()
        setDetailsInViews()

        onLoading()
        cocktailDetailsViewModel.getCocktailDetails(cocktailId)
        observeCocktailDetails()
    }

    private fun observeCocktailDetails() {
        cocktailDetailsViewModel.observeCocktailDetails().observe(this) { cocktailDetails ->
            binding.tvCategoryInfo.text = cocktailDetails.strCategory
            binding.tvGlassInfo.text = cocktailDetails.strGlass
            binding.tvContent.text = cocktailDetails.strInstructions
            onResponse()
        }
    }

    private fun setDetailsInViews() {
        Glide.with(this).load(cocktailImage).into(binding.imgCocktailDetail)
        binding.collapsingToolbar.title = cocktailName
    }

    private fun getCocktailDetailsFromIntent() {
        val intent = intent
        cocktailId = intent.getStringExtra(HomeFragment.COCKTAIL_ID)!!
        cocktailName = intent.getStringExtra(HomeFragment.COCKTAIL_NAME)!!
        cocktailImage = intent.getStringExtra(HomeFragment.COCKTAIL_IMAGE)!!
    }

    private fun onLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.fabAddFavorite.visibility = View.GONE
        binding.tvCategoryInfo.visibility = View.GONE
        binding.tvGlassInfo.visibility = View.GONE
    }

    private fun onResponse() {
        binding.progressBar.visibility = View.GONE
        binding.fabAddFavorite.visibility = View.VISIBLE
        binding.tvCategoryInfo.visibility = View.VISIBLE
        binding.tvGlassInfo.visibility = View.VISIBLE
    }
}