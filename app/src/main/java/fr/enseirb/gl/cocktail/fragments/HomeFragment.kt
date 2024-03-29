package fr.enseirb.gl.cocktail.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import fr.enseirb.gl.cocktail.activities.CategoryCocktailsActivity
import fr.enseirb.gl.cocktail.activities.CocktailDetailsActivity
import fr.enseirb.gl.cocktail.activities.MainActivity
import fr.enseirb.gl.cocktail.adapters.CategoriesAdapter
import fr.enseirb.gl.cocktail.adapters.RecentViewedCocktailAdapter
import fr.enseirb.gl.cocktail.databinding.FragmentHomeBinding
import fr.enseirb.gl.cocktail.models.Drink
import fr.enseirb.gl.cocktail.models.RecentViewedCocktail
import fr.enseirb.gl.cocktail.mvvm.HomeViewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var randomCocktail: Drink
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var recentViewedCocktailAdapter: RecentViewedCocktailAdapter
    private val spacing = 30

    companion object {
        const val COCKTAIL_ID = "cocktail_id"
        const val COCKTAIL_NAME = "cocktail_name"
        const val COCKTAIL_IMAGE = "cocktail_image"
        const val FILTER_NAME = "category_name"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeViewModel = (activity as MainActivity).viewModel
        recentViewedCocktailAdapter = RecentViewedCocktailAdapter()
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

        onLoading()

        homeViewModel.getRandomCocktail()
        observeRandomCocktail()
        onRandomCocktailClick()

        prepareCategoriesRecyclerView()
        prepareRecentCocktailRecyclerView()
        homeViewModel.getCategories()
        observeCategories()
        onCategoryClick()

        homeViewModel.getRecentViewedCocktails()
        observeRecentViewedCocktails()

        Handler(Looper.getMainLooper()).postDelayed({
            onResponse()
        }, 2000)
    }

    private fun onLoading() {
        binding.homeProgressCircle.visibility = View.VISIBLE
        binding.randomCocktailImage.visibility = View.GONE
        binding.categoriesTextview.visibility = View.GONE
        binding.categoriesRecyclerview.visibility = View.GONE
        binding.lastCocktailsTextview.visibility = View.GONE
        binding.lastCocktailsRecyclerview.visibility = View.GONE
    }

    private fun onResponse() {
        binding.homeProgressCircle.visibility = View.GONE
        binding.randomCocktailImage.visibility = View.VISIBLE
        binding.categoriesTextview.visibility = View.VISIBLE
        binding.categoriesRecyclerview.visibility = View.VISIBLE
        binding.lastCocktailsTextview.visibility = View.VISIBLE
        binding.lastCocktailsRecyclerview.visibility = View.VISIBLE
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.getRecentViewedCocktails()
    }

    private fun prepareRecentCocktailRecyclerView() {
        binding.lastCocktailsRecyclerview.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = recentViewedCocktailAdapter
        }
    }

    private fun observeRecentViewedCocktails() {
        homeViewModel.observeRecentViewedCocktails().observe(
            viewLifecycleOwner
        ) { recentCocktailList ->
            if (recentCocktailList.isEmpty()) {
                // If the recentCocktailList is empty, hide the part where recent viewed cocktails are displayed
                binding.lastCocktailsTextview.visibility = View.GONE
                binding.lastCocktailsRecyclerview.visibility = View.GONE
            } else {
                // If the recentCocktailList is not empty, show the part where recent viewed cocktails are displayed
                binding.lastCocktailsTextview.visibility = View.VISIBLE
                binding.lastCocktailsRecyclerview.visibility = View.VISIBLE
                recentViewedCocktailAdapter.updateRecentViewedCocktails(recentCocktailList as MutableList<RecentViewedCocktail>)
            }
        }
    }

    private fun onCategoryClick() {
        categoriesAdapter.onCategoryClick = { category ->
            val intent = Intent(context, CategoryCocktailsActivity::class.java)
            intent.putExtra(FILTER_NAME, category.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareCategoriesRecyclerView() {
        categoriesAdapter = CategoriesAdapter(spacing)
        binding.categoriesRecyclerview.apply {
            layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter

            // Add ItemDecoration to the RecyclerView
            addItemDecoration(CategoriesAdapter.CategoryItemDecoration(spacing))
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