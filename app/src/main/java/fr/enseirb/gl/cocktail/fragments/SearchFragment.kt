package fr.enseirb.gl.cocktail.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import fr.enseirb.gl.cocktail.R
import fr.enseirb.gl.cocktail.activities.MainActivity
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
    private var selectedCategory : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
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

        prepareCategoryFilter(filterDialog)

        filterDialog.findViewById<Button>(R.id.button_cancel).setOnClickListener {
            dialog.dismiss()
        }

        filterDialog.findViewById<Button>(R.id.button_filter).setOnClickListener {
            searchCocktailsAdapter.filterCocktails(selectedCategory)
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun prepareCategoryFilter(filterDialog: View) {
        val categories = listOf("Ordinary Drink", "Cocktail", "Shake")
        val autoComplete : AutoCompleteTextView = filterDialog.findViewById(R.id.textview_filter)
        val adapter = ArrayAdapter(requireContext(), R.layout.filter_list_item, categories)

        autoComplete.setAdapter(adapter)
        if (selectedCategory.isNotEmpty()) {
            autoComplete.setText(selectedCategory, false)
        }
        autoComplete.onItemClickListener = AdapterView.OnItemClickListener { adapterView, _, position, _ ->
            val selectedItem = adapterView.getItemAtPosition(position).toString()
            selectedCategory = selectedItem
        }
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