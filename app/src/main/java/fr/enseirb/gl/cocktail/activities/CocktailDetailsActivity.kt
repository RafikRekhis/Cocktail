package fr.enseirb.gl.cocktail.activities

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import fr.enseirb.gl.cocktail.R
import fr.enseirb.gl.cocktail.databinding.ActivityCocktailDetailsBinding
import fr.enseirb.gl.cocktail.fragments.HomeFragment
import fr.enseirb.gl.cocktail.models.RecentViewedCocktail
import fr.enseirb.gl.cocktail.models.SavedCocktail
import fr.enseirb.gl.cocktail.mvvm.CocktailDetailsViewModel
import fr.enseirb.gl.cocktail.mvvm.HomeViewModel

class CocktailDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCocktailDetailsBinding
    private lateinit var cocktailId: String
    private lateinit var cocktailImage: String
    private lateinit var cocktailDetailsViewModel: CocktailDetailsViewModel
    private lateinit var viewModel: HomeViewModel
    private var drinkToSave: SavedCocktail? = null
    private var recentDrinkToSave: RecentViewedCocktail? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = HomeViewModel(getSharedPreferences("sharedPref", MODE_PRIVATE))

        binding = ActivityCocktailDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarDetails)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        cocktailDetailsViewModel =
            CocktailDetailsViewModel(getSharedPreferences("sharedPref", MODE_PRIVATE))

        getCocktailDetailsFromIntent()
        setDetailsInViews()

        onLoading()
        cocktailDetailsViewModel.getCocktailDetails(cocktailId)
        observeCocktailDetails()

        handleFavoritesButton()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun firstColorFavoriteButton() {
        drinkToSave?.let { it1 ->
            if (cocktailDetailsViewModel.isFavorite(it1)) {
                binding.fabAddFavorite.imageTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.red))
            } else {
                binding.fabAddFavorite.imageTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
            }
        }
    }

    private fun handleFavoritesButton() {
        binding.fabAddFavorite.setOnClickListener {
            drinkToSave?.let { it1 ->
                if (!cocktailDetailsViewModel.isFavorite(it1)) {
                    cocktailDetailsViewModel.addCocktailToFavorite(it1)
                    binding.fabAddFavorite.imageTintList =
                        ColorStateList.valueOf(ContextCompat.getColor(this, R.color.red))
                    Toast.makeText(this, "Cocktail added to favorites", Toast.LENGTH_SHORT).show()
                } else {
                    cocktailDetailsViewModel.removeFavorite(it1)
                    binding.fabAddFavorite.imageTintList =
                        ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
                    Toast.makeText(this, "Cocktail removed from favorites", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun addRecentViewedCocktail() {
        recentDrinkToSave?.let { it1 ->
            cocktailDetailsViewModel.addRecentViewedCocktail(it1)
        }
    }

    private fun observeCocktailDetails() {
        cocktailDetailsViewModel.observeCocktailDetails().observe(this) { cocktailDetails ->

            drinkToSave = SavedCocktail(
                cocktailDetails.idDrink,
                cocktailDetails.strDrink,
                cocktailDetails.strDrinkThumb
            )
            recentDrinkToSave =
                RecentViewedCocktail(cocktailDetails.idDrink, cocktailDetails.strDrinkThumb)

            addRecentViewedCocktail()

            firstColorFavoriteButton()
            binding.collapsingToolbar.title = cocktailDetails.strDrink
            binding.tvCategoryInfo.text = cocktailDetails.strCategory
            binding.tvGlassInfo.text = cocktailDetails.strGlass
            binding.tvAlcoholInfo.text = "${binding.tvAlcoholInfo.text}${cocktailDetails.strAlcoholic}"
            binding.tvIngredientsContent.text = cocktailDetails.getIngredientsWithMeasures()
            binding.tvContent.text = cocktailDetails.strInstructions
            onResponse()
        }
    }

    private fun setDetailsInViews() {
        Glide.with(this).load(cocktailImage).into(binding.imgCocktailDetail)
    }

    private fun getCocktailDetailsFromIntent() {
        val intent = intent
        cocktailId = intent.getStringExtra(HomeFragment.COCKTAIL_ID)!!
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