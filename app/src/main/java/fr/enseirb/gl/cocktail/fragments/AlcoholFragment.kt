package fr.enseirb.gl.cocktail.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import fr.enseirb.gl.cocktail.activities.AlcoholCocktailsActivity
import fr.enseirb.gl.cocktail.activities.MainActivity
import fr.enseirb.gl.cocktail.adapters.AlcoholAdapter
import fr.enseirb.gl.cocktail.adapters.CategoriesAdapter
import fr.enseirb.gl.cocktail.databinding.FragmentAlcoholBinding
import fr.enseirb.gl.cocktail.mvvm.HomeViewModel

class AlcoholFragment : Fragment() {
    private lateinit var binding: FragmentAlcoholBinding
    private lateinit var alcoholAdapter: AlcoholAdapter
    private lateinit var viewModel: HomeViewModel
    private val spacing = 40

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlcoholBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareAlcoholRecyclerView()
        viewModel.getAlcohol()
        observeAlcohol()
        onAlcoholClick()
    }

    private fun onAlcoholClick() {
        alcoholAdapter.onAlcoholClick = { alcohol ->
            val intent = Intent(context, AlcoholCocktailsActivity::class.java)
            intent.putExtra(HomeFragment.FILTER_NAME, alcohol.strAlcoholic)
            startActivity(intent)
        }
    }

    private fun prepareAlcoholRecyclerView() {
        alcoholAdapter = AlcoholAdapter(spacing)
        binding.rvAlcohol.apply {
            layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
            adapter = alcoholAdapter

            addItemDecoration(AlcoholAdapter.AlcoholItemDecoration(spacing))
        }
    }

    private fun observeAlcohol() {
        viewModel.observeAlcohol().observe(viewLifecycleOwner) { alcohol ->
            alcoholAdapter.setAlcohol(alcohol)
        }
    }
}