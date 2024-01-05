package fr.enseirb.gl.cocktail.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import fr.enseirb.gl.cocktail.R
import fr.enseirb.gl.cocktail.activities.MainActivity
import fr.enseirb.gl.cocktail.databinding.FragmentSettingsBinding
import fr.enseirb.gl.cocktail.mvvm.SettingsViewModel

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var settingsViewModel: SettingsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        settingsViewModel = (activity as MainActivity).settingsViewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSettingsBinding.bind(view)
        binding.etRecentCocktailNumber.setText(
            settingsViewModel.getNbMaxRecentCocktails().toString()
        )
        binding.switchNightMode.isChecked = settingsViewModel.getNightMode()
        binding.switchNightMode.setOnCheckedChangeListener { _, isChecked ->
            settingsViewModel.handleNightModeChange(isChecked)
            binding.root.requestFocus()
        }

        binding.etRecentCocktailNumber.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val imm =
                    activity?.getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as? android.view.inputmethod.InputMethodManager
                if (imm?.isActive == true) {
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                    view.clearFocus() // Clear focus from the current view
                }

                val result: Boolean = settingsViewModel.handleSaveClicked(
                    binding.etRecentCocktailNumber.text.toString().toInt()
                )

                if (result) {
                    Toast.makeText(context, "Settings were updated successfully", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(
                        context,
                        "An error occurred while updating settings",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            true
        }
    }
}