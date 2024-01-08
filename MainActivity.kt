package com.carlosvpinto.anotardomino

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toolbar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.carlosvpinto.anotardomino.databinding.ActivityMainBinding
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var analytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        // Obtain the FirebaseAnalytics instance.
        analytics = Firebase.analytics

//        supportActionBar?.setDisplayShowHomeEnabled(true) ********************agrega el icono en la appbar
//        supportActionBar?.setIcon(R.mipmap.ic_launcher)
        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_setting, R.id.nav_slideshow,
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
    fun Context.dpToPx(dp: Int): Int {
        val density = resources.displayMetrics.density
        return (dp * density).toInt()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                // Lógica para el ítem de configuración
                return true
            }
            R.id.action_nocturno -> {
                // Lógica para el ítem "Nocturno"
                // Por ejemplo, cambia el modo nocturno de la aplicación
                habilitarModoNocturno()
                return true
            }
            // Agrega más casos según sea necesario para otros ítems del menú
            else -> return super.onOptionsItemSelected(item)
        }
    }
    private fun cambiarModoNocturno() {
        // Agrega la lógica para cambiar el modo nocturno aquí
        // Puedes utilizar AppCompatDelegate para cambiar el modo nocturno de la aplicación
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
            // Actualmente en modo nocturno, cambia a modo diurno
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            // Actualmente en modo diurno, cambia a modo nocturno
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        // Recrea la actividad para aplicar el cambio
        recreate()
    }
    private fun habilitarModoNocturno() {
        // Cambia el modo nocturno directamente en la aplicación (puede afectar otras actividades/fragmentos)
        // Obtén el estado actual del modo nocturno
        val nightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

// Comprueba si el modo nocturno está activo
        if (nightMode == Configuration.UI_MODE_NIGHT_YES) {

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

            // Si deseas aplicar inmediatamente el cambio en este fragmento
           recreate()
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

            // Si deseas aplicar inmediatamente el cambio en este fragmento
           recreate()
        }
    }

}