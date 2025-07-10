package com.example.centsoapp


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.centsoapp.activities.EmailVerificationActivity
import com.example.centsoapp.databinding.ActivityMainBinding
import com.example.centsoapp.fragments.*
import com.example.centsoapp.utils.SharedPrefsHelper

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar.root)


        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_dashboard -> loadFragment(DashboardFragment())
                R.id.navigation_transactions -> loadFragment(TransactionsFragment())
                R.id.navigation_budget -> loadFragment(BudgetFragment())
                R.id.navigation_backup -> loadFragment(BackupFragment())
            }
            true
        }
        // Default fragment
        loadFragment(DashboardFragment())
    }

    private fun loadFragment(fragment: androidx.fragment.app.Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_logout -> {
                SharedPrefsHelper.clearEmail(this)
                startActivity(Intent(this, EmailVerificationActivity::class.java))
                finish()
                return true
            }
            R.id.menu_reset -> {
                AlertDialog.Builder(this)
                    .setTitle("Reset All Data")
                    .setMessage("Are you sure you want to reset all data?")
                    .setPositiveButton("Yes") { _, _ ->
                        SharedPrefsHelper.resetAll(this)
                        recreate()
                    }
                    .setNegativeButton("No", null)
                    .show()
                return true
            }
            R.id.menu_currency -> {
                val currencies = arrayOf("LKR", "USD", "EUR", "INR")
                AlertDialog.Builder(this)
                    .setTitle("Select Currency")
                    .setItems(currencies) { _, which ->
                        SharedPrefsHelper.saveCurrency(this, currencies[which])
                        recreate()
                    }
                    .show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}