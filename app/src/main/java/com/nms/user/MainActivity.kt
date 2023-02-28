package com.nms.user

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nms.user.fragments.AccountFragment
import com.nms.user.fragments.CustomOrderFragment
import com.nms.user.fragments.HomeFragment
import com.nms.user.fragments.NotificationFragment

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavView: BottomNavigationView

    private lateinit var homeFragment: HomeFragment
    private lateinit var notificationFragment: NotificationFragment
    private lateinit var accountFragment: AccountFragment
    private lateinit var customOrderFragment: CustomOrderFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Hide Focus on EditText when activity starts
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        // Initialize
        bottomNavView = findViewById(R.id.bottomNav)
        bottomNavView.setOnItemSelectedListener(::bottomNavItemSelected)
        homeFragment = HomeFragment()
        notificationFragment = NotificationFragment()
        accountFragment = AccountFragment()
        customOrderFragment = CustomOrderFragment()

        // Get intent data
        val intent = intent
        when (intent.getStringExtra("fragment")) {
            "notification" -> showNotificationFragment()
            "account" -> showAccountFragment()
            "categories" -> showCategoriesFragment()
            else -> showHomeFragment()
        }
    }

    private fun bottomNavItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuHome -> showHomeFragment()
            R.id.menuNotification -> showNotificationFragment()
            R.id.menuMyCart -> openMyCartActivity()
            R.id.menuAccount -> showAccountFragment()
            R.id.menuCategories -> showCategoriesFragment()
            else -> return false
        }
        return true
    }

    private fun showHomeFragment() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, homeFragment)
            commit()
        }
    }

    private fun showNotificationFragment() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, notificationFragment)
            commit()
        }
    }

    private fun openMyCartActivity() {
        val intent = Intent(this, CartActivity::class.java)
        startActivity(intent)
    }

    private fun showAccountFragment() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, accountFragment)
            commit()
        }
    }

    private fun showCategoriesFragment() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, customOrderFragment)
            commit()
        }
    }

}