package com.netplix.id


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.netplix.id.fragment.DashboardFragment
import com.netplix.id.fragment.ProfileFragment
import com.netplix.id.fragment.SearchFragment


class MainActivity : AppCompatActivity() {

    // Initialise the DrawerLayout, NavigationView and ToggleBar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

// Call findViewById on the DrawerLayout
        drawerLayout = findViewById(R.id.drawerLayout)
        // Pass the ActionBarToggle action into the drawerListener
        actionBarToggle = ActionBarDrawerToggle(this, drawerLayout, 0, 0)
        drawerLayout.addDrawerListener(actionBarToggle)
        // Display the hamburger icon to launch the drawer
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Call syncState() on the action bar so it'll automatically change to the back button when the drawer layout is open
        actionBarToggle.syncState()
        // Call findViewById on the NavigationView
        navView = findViewById(R.id.navView)
        // Call setNavigationItemSelectedListener on the NavigationView to detect when items are clicked
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.myProfile -> {
                    setFragment(ProfileFragment())
                    true
                }
                else -> {
                    false
                }
            }
        }

        setFragment(DashboardFragment())
    }

    fun setFragment(fr : Fragment){
        val frag = supportFragmentManager.beginTransaction()
        frag.replace(R.id.fl_view,fr)
        frag.commit()
    }


    // override the onSupportNavigateUp() function to launch the Drawer when the hamburger icon is clicked
    override fun onSupportNavigateUp(): Boolean {
        drawerLayout.openDrawer(navView)
        return true
    }

    @Override
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_action_right, menu)
        return true
    }

    @Override
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()
        return if (id == R.id.action_search) {
            // Tambahkan kode untuk menangani pencarian di sini
            setFragment(SearchFragment())
            true
        } else super.onOptionsItemSelected(item)
    }

    // override the onBackPressed() function to close the Drawer when the back button is clicked
    override fun onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            setFragment(DashboardFragment())
            this.drawerLayout.closeDrawer(GravityCompat.START)
        }
        else {
            super.onBackPressed()
        }
    }
}