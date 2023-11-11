package fr.enseirb.gl.cocktail.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import fr.enseirb.gl.cocktail.R
import fr.enseirb.gl.cocktail.adapters.SearchCocktailsAdapter
import fr.enseirb.gl.cocktail.databinding.FragmentSearchBinding
import fr.enseirb.gl.cocktail.mvvm.HomeViewModel

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var searchCocktailsAdapter: SearchCocktailsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = HomeViewModel()
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
            searchCocktails()
        }

        observeSearchCocktails()
    }

    private fun observeSearchCocktails() {
        viewModel.observeSearchedCocktails().observe(viewLifecycleOwner) { cocktails ->
            searchCocktailsAdapter.setCocktails(cocktails)
        }
    }

    private fun searchCocktails() {
        val cocktailName = binding.etSearch.text.toString()
        if(cocktailName.isNotEmpty()) {
            viewModel.searchCocktailsByName(cocktailName)
        }
    }

    private fun prepareSearchRecyclerView() {
        searchCocktailsAdapter = SearchCocktailsAdapter()
        binding.rvSearch.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = searchCocktailsAdapter
        }
    }
}