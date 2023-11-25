package fr.enseirb.gl.cocktail.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import fr.enseirb.gl.cocktail.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

       /* Handler().postDelayed({
            // Créer une intention pour démarrer MainActivity
            val intent = Intent(this@SplashActivity, MainActivity::class.java)

            // Démarrer l'activité MainActivity
            startActivity(intent)

            // Fermer l'activité SplashActivity
            finish()
        }, 1900)*/

        GlobalScope.launch {
            delay(1900) // Attendez 1,9 seconde
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish() // Fermez l'activité SplashActivity
        }


    }
}