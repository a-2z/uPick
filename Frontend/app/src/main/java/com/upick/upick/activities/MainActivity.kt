package com.upick.upick.activities

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.upick.upick.R
import com.upick.upick.databinding.ActivityMainBinding

class Keys private constructor() {
    companion object {
        const val LOGGED_IN = "logged_in"
    }
}

class MainActivity : AppCompatActivity() {

    internal lateinit var sharedPreferences: SharedPreferences
    internal lateinit var mainActivityBinding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)

        setTheme(R.style.AppTheme_NoActionBar)  // To remove splash
        setContentView(mainActivityBinding.root)
        setSupportActionBar(mainActivityBinding.toolbar)

        navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.DashboardFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            mainActivityBinding.toolbar.visibility = when (destination.id) {
                R.id.startUpFragment, R.id.loginFragment, R.id.signUpFragment -> View.GONE
                else -> View.VISIBLE
            }
        }

//        binding.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}