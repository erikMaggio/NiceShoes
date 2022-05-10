package com.example.ventazapas

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.ventazapas.AppNiceShoes.Companion.preferences
import com.example.ventazapas.databinding.ActivityDrawerBinding
import com.example.ventazapas.databinding.NavHeaderDrawerBinding
import com.example.ventazapas.utils.Globals.NAME
import com.example.ventazapas.utils.Globals.OBJECT_USER

class DrawerActivity : AppCompatActivity() {

    private lateinit var navigationView: NavigationView
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var bindingNav: NavHeaderDrawerBinding
    private lateinit var binding: ActivityDrawerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDrawerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarDrawer.toolbar)


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_drawer)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home_fragment,
                R.id.nav_shoes_fragment,
                R.id.nav_my_orders,
                R.id.nav_stateAccountFragment,
                R.id.nav_favoriteFragment,
                R.id.nav_offersFragment,
                R.id.nav_purchaseHistoryFragment,
                R.id.nav_addShoesFragment,
                R.id.nav_sellShoesFragment,
                R.id.nav_changePriceFragment,
                R.id.nav_stateAccountAdminFragment,
                R.id.nav_clientFragment
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        val hView: View = navigationView.getHeaderView(0)
        bindingNav = NavHeaderDrawerBinding.bind(hView)

        checkUser(OBJECT_USER.type)
        binding.btLogOut.setOnClickListener {
            preferences.clear()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.drawer, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_drawer)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()

    }

    fun checkUser(type: String) {
        if ( type == "admin") {
            /** Admin */
            navigationView.menu.findItem(R.id.nav_home_fragment).isVisible = true
            navigationView.menu.findItem(R.id.nav_shoes_fragment).isVisible= false
            navigationView.menu.findItem(R.id.nav_favoriteFragment).isVisible= false
            navigationView.menu.findItem(R.id.nav_offersFragment).isVisible= false
            navigationView.menu.findItem(R.id.nav_my_orders).isVisible= false
            navigationView.menu.findItem(R.id.nav_purchaseHistoryFragment).isVisible= false
            navigationView.menu.findItem(R.id.nav_stateAccountFragment).isVisible= false
            bindingNav.tvName.text= NAME
        } else {
            /** Client */
            navigationView.menu.findItem(R.id.nav_home_fragment).isVisible = true
            navigationView.menu.findItem(R.id.nav_modifyShoeListFragment).isVisible= false
            navigationView.menu.findItem(R.id.nav_addShoesFragment).isVisible = false
            navigationView.menu.findItem(R.id.nav_sellShoesFragment).isVisible = false
            navigationView.menu.findItem(R.id.nav_changePriceFragment).isVisible = false
            navigationView.menu.findItem(R.id.nav_stateAccountAdminFragment).isVisible = false
            navigationView.menu.findItem(R.id.nav_clientFragment).isVisible = false
           bindingNav.tvName.text= NAME
        }
    }
}