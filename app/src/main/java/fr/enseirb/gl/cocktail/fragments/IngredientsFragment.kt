package fr.enseirb.gl.cocktail.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import fr.enseirb.gl.cocktail.activities.IngredientCocktailsActivity
import fr.enseirb.gl.cocktail.activities.MainActivity
import fr.enseirb.gl.cocktail.adapters.GlassAdapter
import fr.enseirb.gl.cocktail.adapters.IngredientsAdapter
import fr.enseirb.gl.cocktail.databinding.FragmentIngredientsBinding
import fr.enseirb.gl.cocktail.mvvm.HomeViewModel

class IngredientsFragment : Fragment() {
    private lateinit var binding: FragmentIngredientsBinding
    private lateinit var ingredientsAdapter: IngredientsAdapter
    private lateinit var viewModel: HomeViewModel

    private val spacing = 30

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIngredientsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareIngredientsRecyclerView()
        viewModel.getIngredients()
        observeIngredients()
        onIngredientClick()
    }

    private fun onIngredientClick() {
        ingredientsAdapter.onIngredientClick = { ingredient ->
            val intent = Intent(context, IngredientCocktailsActivity::class.java)
            intent.putExtra(HomeFragment.FILTER_NAME, ingredient.strIngredient1)
            startActivity(intent)
        }
    }

    private fun prepareIngredientsRecyclerView() {
        ingredientsAdapter = IngredientsAdapter(spacing)
        binding.rvIngredients.apply {
            layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
            adapter = ingredientsAdapter

            addItemDecoration(IngredientsAdapter.IngredientItemDecoration(spacing))
        }
    }

    private fun observeIngredients() {
        viewModel.observeIngredients().observe(viewLifecycleOwner) { ingredients ->
            ingredientsAdapter.setIngredients(ingredients)
        }
    }
}