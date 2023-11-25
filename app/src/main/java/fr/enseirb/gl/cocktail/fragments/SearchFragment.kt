package fr.enseirb.gl.cocktail.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import fr.enseirb.gl.cocktail.R
import fr.enseirb.gl.cocktail.activities.MainActivity
import fr.enseirb.gl.cocktail.adapters.AlcoholFilterAdapter
import fr.enseirb.gl.cocktail.adapters.CategoryFilterAdapter
import fr.enseirb.gl.cocktail.adapters.GlassFilterAdapter
import fr.enseirb.gl.cocktail.adapters.IngredientFilterAdapter
import fr.enseirb.gl.cocktail.adapters.SearchCocktailsAdapter
import fr.enseirb.gl.cocktail.databinding.FragmentSearchBinding
import fr.enseirb.gl.cocktail.mvvm.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var searchCocktailsAdapter: SearchCocktailsAdapter
    private lateinit var categoryFilterAdapter: CategoryFilterAdapter
    private lateinit var ingredientFilterAdapter: IngredientFilterAdapter
    private lateinit var glassFilterAdapter: GlassFilterAdapter
    private lateinit var alcoholFilterAdapter: AlcoholFilterAdapter
    private var selectedCategory: String = ""
    private var selectedIngredient: String = ""
    private var selectedGlass: String = ""
    private var selectedCocktailType: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        viewModel.getIngredients()
        viewModel.getGlass()
        viewModel.getAlcohol()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareSearchRecyclerView()

        binding.ivSearch.setOnClickListener {
            searchCocktailsAdapter.resetFilter()
            searchCocktails()
        }

        observeSearchCocktails()

        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val imm = activity?.getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as? android.view.inputmethod.InputMethodManager
                imm?.hideSoftInputFromWindow(view.windowToken, 0)
            }
            true
        }

        var searchJob: Job? = null
        binding.etSearch.addTextChangedListener { searchQuery ->
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(625)
                if (searchQuery.toString().isNotEmpty()) {
                    searchCocktailsAdapter.resetFilter()
                    viewModel.searchCocktailsByName(searchQuery.toString())
                } else {
                    searchCocktailsAdapter.setCocktails(ArrayList())
                }
            }
        }

        binding.ivFilter.setOnClickListener {
            searchCocktailsAdapter.resetFilter()
            showFilterDialog()
        }
    }

    private fun showFilterDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val filterDialog = layoutInflater.inflate(R.layout.popup_window, null)
        builder.setView(filterDialog)
        val dialog = builder.create()

        categoryFilterAdapter = CategoryFilterAdapter(
            requireContext(),
            filterDialog.findViewById(R.id.tv_filter_category),
            viewModel.observeCategories().value!!,
            selectedCategory
        )

        ingredientFilterAdapter = IngredientFilterAdapter(
            requireContext(),
            filterDialog.findViewById(R.id.tv_filter_ingredient),
            viewModel.observeIngredients().value!!,
            selectedIngredient
        )

        glassFilterAdapter = GlassFilterAdapter(
            requireContext(),
            filterDialog.findViewById(R.id.tv_filter_glass),
            viewModel.observeGlass().value!!,
            selectedGlass
        )

        alcoholFilterAdapter = AlcoholFilterAdapter(
            requireContext(),
            filterDialog.findViewById(R.id.tv_filter_alcohol),
            viewModel.observeAlcohol().value!!,
            selectedCocktailType
        )

        filterDialog.findViewById<Button>(R.id.button_cancel).setOnClickListener {
            dialog.dismiss()
        }

        filterDialog.findViewById<Button>(R.id.button_filter).setOnClickListener {
            selectedCategory = categoryFilterAdapter.getSelectedCategory()
            selectedIngredient = ingredientFilterAdapter.getSelectedIngredient()
            selectedGlass = glassFilterAdapter.getSelectedGlass()
            selectedCocktailType = alcoholFilterAdapter.getSelectedAlcohol()
            searchCocktailsAdapter.filterCocktails(selectedCategory, selectedIngredient, selectedGlass, selectedCocktailType)
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun observeSearchCocktails() {
        viewModel.observeSearchedCocktails().observe(viewLifecycleOwner) { cocktails ->
            if (cocktails == null) {
                searchCocktailsAdapter.setCocktails(ArrayList())
            } else {
                searchCocktailsAdapter.setCocktails(cocktails)
            }
        }
    }

    private fun searchCocktails() {
        val cocktailName = binding.etSearch.text.toString()
        viewModel.searchCocktailsByName(cocktailName)
    }

    private fun prepareSearchRecyclerView() {
        searchCocktailsAdapter = SearchCocktailsAdapter()
        binding.rvSearch.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = searchCocktailsAdapter
        }
    }
}