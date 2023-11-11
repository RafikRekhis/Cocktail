package fr.enseirb.gl.cocktail.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import fr.enseirb.gl.cocktail.R
import fr.enseirb.gl.cocktail.activities.CategoryCocktailsActivity
import fr.enseirb.gl.cocktail.activities.MainActivity
import fr.enseirb.gl.cocktail.adapters.CategoriesAdapter
import fr.enseirb.gl.cocktail.databinding.FragmentCategoriesBinding
import fr.enseirb.gl.cocktail.mvvm.HomeViewModel

class CategoriesFragment : Fragment() {
    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = HomeViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareCategoriesRecyclerView()
        viewModel.getCategories()
        observeCategories()
        onCategoryClick()
    }

    private fun onCategoryClick() {
        categoriesAdapter.onCategoryClick = { category ->
            val intent = Intent(context, CategoryCocktailsActivity::class.java)
            intent.putExtra(HomeFragment.CATEGORY_NAME, category.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareCategoriesRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding.rvCategories.apply {
            layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }

    private fun observeCategories() {
        viewModel.observeCategories().observe(viewLifecycleOwner) { categories ->
            categoriesAdapter.setCategories(categories)
        }
    }
}