package fr.enseirb.gl.cocktail.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import fr.enseirb.gl.cocktail.activities.MainActivity
import fr.enseirb.gl.cocktail.adapters.FavoritesCocktailsAdapter
import fr.enseirb.gl.cocktail.databinding.FragmentFavoritesBinding
import fr.enseirb.gl.cocktail.mvvm.HomeViewModel


class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var favoritesCocktailsAdapter: FavoritesCocktailsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getFavorites()
        prepareRecyclerView()
        observeFavorites()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavorites()
    }

    private fun prepareRecyclerView() {
        favoritesCocktailsAdapter = FavoritesCocktailsAdapter()
        binding.recyclerViewFavorites.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = favoritesCocktailsAdapter
        }
    }

    private fun observeFavorites() {
        // TODO : change requireActivity() with a better solution
        viewModel.observeFavorites().observe(requireActivity()) { cocktails ->
            favoritesCocktailsAdapter.differ.submitList(cocktails)
        }
    }
}