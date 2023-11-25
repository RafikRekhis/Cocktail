package fr.enseirb.gl.cocktail.mvvm

import android.content.SharedPreferences
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel

class SettingsViewModel(private val sharedPreferences: SharedPreferences) : ViewModel() {

    public fun getNbMaxRecentCocktails(): Int {
        return sharedPreferences.getInt("maxRecentItems", 5)
    }

    public fun handleSaveClicked(nbMaxRecentCocktails: Int,isChecked: Boolean): Boolean {
        if(nbMaxRecentCocktails < 0) {
            return false
        }
        sharedPreferences.edit().putBoolean("nightMode", isChecked).apply()
        if (isChecked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        sharedPreferences.edit().putInt("maxRecentItems", nbMaxRecentCocktails).apply()
        return true
    }

    public fun getNightMode(): Boolean {
        return sharedPreferences.getBoolean("nightMode", true)
    }

    public fun handleNightModeChange(isChecked: Boolean) {
        sharedPreferences.edit().putBoolean("nightMode", isChecked).apply()
    }


}