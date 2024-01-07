package fr.enseirb.gl.cocktail.adapters

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.AdapterView
import fr.enseirb.gl.cocktail.R
import fr.enseirb.gl.cocktail.models.Category

class CategoryFilterAdapter(
    private val context: Context,
    private val autoCompleteTextView: AutoCompleteTextView,
    private val categories: List<Category>,
    private var selectedCategory: String
) {
    init {
        setupAdapter()
        setupAutoCompleteTextView()
    }

    private fun setupAdapter() {
        val categoryNames = mutableListOf<String>()
        categoryNames.add("")
        for (category in categories) {
            categoryNames.add(category.strCategory)
        }

        val adapter =
            ArrayAdapter(context, R.layout.filter_list_item, categoryNames)
        autoCompleteTextView.setAdapter(adapter)

        if (selectedCategory.isNotEmpty()) {
            autoCompleteTextView.setText(selectedCategory, false)
        }
    }

    private fun setupAutoCompleteTextView() {
        autoCompleteTextView.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, _, position, _ ->
                val selectedItem = adapterView.getItemAtPosition(position).toString()
                selectedCategory = selectedItem
            }
    }

    fun getSelectedCategory(): String {
        return selectedCategory
    }
}