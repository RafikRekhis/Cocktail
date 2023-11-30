package fr.enseirb.gl.cocktail.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import fr.enseirb.gl.cocktail.R
import fr.enseirb.gl.cocktail.activities.MainActivity
import fr.enseirb.gl.cocktail.databinding.FragmentSearchBinding
import fr.enseirb.gl.cocktail.databinding.FragmentSettingsBinding
import fr.enseirb.gl.cocktail.mvvm.HomeViewModel
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
        binding.etRecentCocktailNumber.setText(settingsViewModel.getNbMaxRecentCocktails().toString())
        binding.switchNightMode.isChecked = settingsViewModel.getNightMode()
        binding.switchNightMode.setOnCheckedChangeListener { _, isChecked ->
            settingsViewModel.handleNightModeChange(isChecked)
            binding.root.requestFocus()
        }

        addSaveButtonListener()
    }



    private fun addSaveButtonListener() {
        binding.btnSettingsSave.setOnClickListener {
            val result:Boolean = settingsViewModel.handleSaveClicked(binding.etRecentCocktailNumber.text.toString().toInt())

            // Hide keyboard only if it is currently showing
            val imm =
                activity?.getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as? android.view.inputmethod.InputMethodManager
            if (imm?.isActive == true) {
                imm.hideSoftInputFromWindow(view?.windowToken, 0)
                view?.clearFocus() // Clear focus from the current view
            }

            // Show toast
            if(result) {
                Toast.makeText(context, "Paramètres mis à jour avec succès.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Erreur lors de la mise à jour des paramètres.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}