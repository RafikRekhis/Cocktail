package fr.enseirb.gl.cocktail.adapters

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.AdapterView
import fr.enseirb.gl.cocktail.R
import fr.enseirb.gl.cocktail.models.Ingredient

class IngredientFilterAdapter(
    private val context: Context,
    private val autoCompleteTextView: AutoCompleteTextView,
    private val ingredients: List<Ingredient>,
    private var selectedIngredient: String
) {
    init {
        setupAdapter()
        setupAutoCompleteTextView()
    }

    private fun setupAdapter() {
        val ingredientNames = mutableListOf<String>()
        ingredientNames.add("")
        for (ingredient in ingredients) {
            ingredientNames.add(ingredient.strIngredient1)
        }

        val adapter =
            ArrayAdapter(context, R.layout.filter_list_item, ingredientNames)
        autoCompleteTextView.setAdapter(adapter)

        if (selectedIngredient.isNotEmpty()) {
            autoCompleteTextView.setText(selectedIngredient, false)
        }
    }

    private fun setupAutoCompleteTextView() {
        autoCompleteTextView.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, _, position, _ ->
                val selectedItem = adapterView.getItemAtPosition(position).toString()
                selectedIngredient = selectedItem
            }
    }

    fun getSelectedIngredient(): String {
        return selectedIngredient
    }
}