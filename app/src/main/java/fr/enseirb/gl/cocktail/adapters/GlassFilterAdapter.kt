package fr.enseirb.gl.cocktail.adapters

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.AdapterView
import fr.enseirb.gl.cocktail.R
import fr.enseirb.gl.cocktail.models.Glass

class GlassFilterAdapter(
    private val context: Context,
    private val autoCompleteTextView: AutoCompleteTextView,
    private val glasses: List<Glass>,
    private var selectedGlass: String
) {
    init {
        setupAdapter()
        setupAutoCompleteTextView()
    }

    private fun setupAdapter() {
        val glassNames = mutableListOf<String>()
        glassNames.add("")
        for (glass in glasses) {
            glassNames.add(glass.strGlass)
        }

        val adapter =
            ArrayAdapter(context, R.layout.filter_list_item, glassNames)
        autoCompleteTextView.setAdapter(adapter)

        if (selectedGlass.isNotEmpty()) {
            autoCompleteTextView.setText(selectedGlass, false)
        }
    }

    private fun setupAutoCompleteTextView() {
        autoCompleteTextView.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, _, position, _ ->
                val selectedItem = adapterView.getItemAtPosition(position).toString()
                selectedGlass = selectedItem
            }
    }

    fun getSelectedGlass(): String {
        return selectedGlass
    }
}