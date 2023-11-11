package fr.enseirb.gl.cocktail.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.enseirb.gl.cocktail.R
import fr.enseirb.gl.cocktail.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.Bottom_navigation)
        val navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        NavigationUI.setupWithNavController(bottomNavigation, navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settingsFragment -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_homeFragment_to_settingsFragment)
            R.id.searchFragment -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_homeFragment_to_searchFragment)
            R.id.ingredientsFragment -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_homeFragment_to_ingredientsFragment)
            R.id.glassFragment -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_homeFragment_to_glassFragment)
            R.id.alcoholFragment -> findNavController(R.id.nav_host_fragment).navigate(R.id.alcoholFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}