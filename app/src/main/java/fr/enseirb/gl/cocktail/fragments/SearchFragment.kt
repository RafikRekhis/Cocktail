package fr.enseirb.gl.cocktail.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //viewModel = HomeViewModel()

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
            searchCocktails()
        }

        observeSearchCocktails()

        var searchJob: Job? = null
        binding.etSearch.addTextChangedListener { searchQuery ->
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(625)
                if (searchQuery.toString().isNotEmpty()) {
                    viewModel.searchCocktailsByName(searchQuery.toString())
                }
            }
        }
    }

    private fun observeSearchCocktails() {
        viewModel.observeSearchedCocktails().observe(viewLifecycleOwner) { cocktails ->
            searchCocktailsAdapter.setCocktails(cocktails)
        }
    }

    private fun searchCocktails() {
        val cocktailName = binding.etSearch.text.toString()
        if (cocktailName.isNotEmpty()) {
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