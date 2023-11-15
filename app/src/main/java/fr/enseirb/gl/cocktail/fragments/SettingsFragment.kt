package fr.enseirb.gl.cocktail.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import fr.enseirb.gl.cocktail.R
import fr.enseirb.gl.cocktail.activities.MainActivity
import fr.enseirb.gl.cocktail.databinding.FragmentSearchBinding
import fr.enseirb.gl.cocktail.databinding.FragmentSettingsBinding
import fr.enseirb.gl.cocktail.mvvm.HomeViewModel
import fr.enseirb.gl.cocktail.mvvm.SettingsViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSettingsBinding.bind(view)
        binding.etRecentCocktailNumber.setText(settingsViewModel.getNbMaxRecentCocktails().toString())
        addSaveButtonListener()
    }

    private fun addSaveButtonListener() {
        binding.btnSettingsSave.setOnClickListener {
            val result:Boolean = settingsViewModel.handleSaveClicked(binding.etRecentCocktailNumber.text.toString().toInt())
            binding.etRecentCocktailNumber.clearFocus()
            if(result) {
                Toast.makeText(context, "Paramètres mis à jour avec succès.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Erreur lors de la mise à jour des paramètres.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}