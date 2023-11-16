package fr.enseirb.gl.cocktail.mvvm

import android.content.SharedPreferences
import android.widget.Toast
import androidx.lifecycle.ViewModel

class SettingsViewModel(private val sharedPreferences: SharedPreferences) : ViewModel() {

    public fun getNbMaxRecentCocktails(): Int {
        return sharedPreferences.getInt("maxRecentItems", 5)
    }

    public fun handleSaveClicked(nbMaxRecentCocktails: Int): Boolean {
        if(nbMaxRecentCocktails < 1 || nbMaxRecentCocktails > 99) {
            Toast.makeText(null, "Veuillez entrer un nombre entre 1 et 10", Toast.LENGTH_SHORT).show()
            return false
        }
        sharedPreferences.edit().putInt("maxRecentItems", nbMaxRecentCocktails).apply()
        return true
    }


}