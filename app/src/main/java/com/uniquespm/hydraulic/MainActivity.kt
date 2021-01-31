package com.uniquespm.hydraulic

import android.content.Intent
import android.content.Intent.*
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat.START
import androidx.navigation.Navigation
import com.uniquespm.hydraulic.util.Constants.Companion.WEBSITE_URL
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity";
    }

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home, R.id.nav_project, R.id.nav_formula, R.id.nav_convertor, R.id.nav_symbols, R.id.nav_privacy, R.id.nav_share, R.id.nav_catalogue, R.id.nav_share,
            R.id.nav_catalogue, R.id.nav_website, R.id.nav_contactUs), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_share -> {
                    shareApp()
                }
                R.id.nav_website -> {
                    openWebsite()
                }
                else -> {
                    navController.popBackStack()
                    navController.navigate(it.itemId)
                }
            }
            drawerLayout.closeDrawer(START)
            true
        }
    }

    private fun openWebsite() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(WEBSITE_URL)
        startActivity(intent)
    }

    private fun shareApp() {
        val intent = Intent(ACTION_SEND)
        intent.type = "text/plain"
        val shareBody = "Download the app from the following link";
        intent.putExtra(EXTRA_TEXT, shareBody)
        startActivity(Intent.createChooser(intent, "Share via"))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        val navController = findNavController(R.id.nav_host_fragment)
        if (navController.currentDestination?.id == R.id.nav_home) {
            super.onBackPressed()
        } else {
            navController.popBackStack()
            navController.navigate(R.id.nav_home)
        }
    }

}
