package fr.enseirb.gl.cocktail.adapters

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.AdapterView
import fr.enseirb.gl.cocktail.R
import fr.enseirb.gl.cocktail.models.Alcohol

class AlcoholFilterAdapter(
    private val context: Context,
    private val autoCompleteTextView: AutoCompleteTextView,
    private val cocktailTypes: List<Alcohol>,
    private var selectedCocktailType: String
) {
    init {
        setupAdapter()
        setupAutoCompleteTextView()
    }

    private fun setupAdapter() {
        val cocktailTypeNames = mutableListOf<String>()
        cocktailTypeNames.add("")
        for (cocktailType in cocktailTypes) {
            cocktailTypeNames.add(cocktailType.strAlcoholic)
        }

        val adapter =
            ArrayAdapter(context, R.layout.filter_list_item, cocktailTypeNames)
        autoCompleteTextView.setAdapter(adapter)

        if (selectedCocktailType.isNotEmpty()) {
            autoCompleteTextView.setText(selectedCocktailType, false)
        }
    }

    private fun setupAutoCompleteTextView() {
        autoCompleteTextView.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, _, position, _ ->
                val selectedItem = adapterView.getItemAtPosition(position).toString()
                selectedCocktailType = selectedItem
            }
    }

    fun getSelectedAlcohol(): String {
        return selectedCocktailType
    }
}