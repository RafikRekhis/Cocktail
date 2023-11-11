package fr.enseirb.gl.cocktail.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import fr.enseirb.gl.cocktail.R
import fr.enseirb.gl.cocktail.activities.CategoryCocktailsActivity
import fr.enseirb.gl.cocktail.activities.CocktailDetailsActivity
import fr.enseirb.gl.cocktail.adapters.CategoriesAdapter
import fr.enseirb.gl.cocktail.databinding.FragmentHomeBinding
import fr.enseirb.gl.cocktail.models.Drink
import fr.enseirb.gl.cocktail.mvvm.HomeViewModel


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var randomCocktail: Drink
    private lateinit var categoriesAdapter: CategoriesAdapter

    companion object {
        const val COCKTAIL_ID = "cocktail_id"
        const val COCKTAIL_NAME = "cocktail_name"
        const val COCKTAIL_IMAGE = "cocktail_image"
        const val CATEGORY_NAME = "category_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = HomeViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.getRandomCocktail()
        observeRandomCocktail()
        onRandomCocktailClick()

        prepareCategoriesRecyclerView()
        homeViewModel.getCategories()
        observeCategories()
        onCategoryClick()

//        onSearchIconClick()
    }

//    private fun onSearchIconClick() {
//        binding.iconSearch.setOnClickListener {
//            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
//        }
//    }

    private fun onCategoryClick() {
        categoriesAdapter.onCategoryClick = { category ->
            val intent = Intent(context, CategoryCocktailsActivity::class.java)
            intent.putExtra(CATEGORY_NAME, category.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareCategoriesRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding.categoriesRecyclerview.apply {
            layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }

    private fun observeCategories() {
        homeViewModel.observeCategories().observe(viewLifecycleOwner) { categories ->
            categoriesAdapter.setCategories(categories)
        }
    }

    private fun onRandomCocktailClick() {
        binding.randomCocktailImage.setOnClickListener {
            val intent = Intent(context, CocktailDetailsActivity::class.java)
            intent.putExtra(COCKTAIL_ID, randomCocktail.idDrink)
            intent.putExtra(COCKTAIL_NAME, randomCocktail.strDrink)
            intent.putExtra(COCKTAIL_IMAGE, randomCocktail.strDrinkThumb)
            startActivity(intent)
        }
    }

    private fun observeRandomCocktail() {
        homeViewModel.observeRandomCocktail().observe(viewLifecycleOwner) { randomCocktail ->
            this.randomCocktail = randomCocktail
            Glide.with(this)
                .load(randomCocktail!!.strDrinkThumb)
                .into(binding.randomCocktailImage)
        }
    }
}