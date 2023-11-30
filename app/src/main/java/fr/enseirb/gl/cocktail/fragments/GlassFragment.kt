package fr.enseirb.gl.cocktail.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import fr.enseirb.gl.cocktail.activities.GlassCocktailsActivity
import fr.enseirb.gl.cocktail.activities.MainActivity
import fr.enseirb.gl.cocktail.adapters.AlcoholAdapter
import fr.enseirb.gl.cocktail.adapters.GlassAdapter
import fr.enseirb.gl.cocktail.databinding.FragmentGlassBinding
import fr.enseirb.gl.cocktail.mvvm.HomeViewModel

class GlassFragment : Fragment() {
    private lateinit var binding: FragmentGlassBinding
    private lateinit var glassAdapter: GlassAdapter
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
        binding = FragmentGlassBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareGlassRecyclerView()
        viewModel.getGlass()
        observeGlass()
        onGlassClick()
    }

    private fun onGlassClick() {
        glassAdapter.onGlassClick = { glass ->
            val intent = Intent(context, GlassCocktailsActivity::class.java)
            intent.putExtra(HomeFragment.FILTER_NAME, glass.strGlass)
            startActivity(intent)
        }
    }

    private fun prepareGlassRecyclerView() {
        glassAdapter = GlassAdapter(spacing)
        binding.rvGlass.apply {
            layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
            adapter = glassAdapter

            addItemDecoration(GlassAdapter.GlassItemDecoration(spacing))
        }
    }

    private fun observeGlass() {
        viewModel.observeGlass().observe(viewLifecycleOwner) { glass ->
            glassAdapter.setGlass(glass)
        }
    }
}