package com.nms.user

import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nms.user.fragments.*

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavView: BottomNavigationView

    private lateinit var homeFragment: HomeFragment
    private lateinit var notificationFragment: NotificationFragment
    private lateinit var myOrdersFragment: MyOrdersFragment
    private lateinit var accountFragment: AccountFragment
    private lateinit var categoriesFragment: CategoriesFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Hide Focus on EditText when activity starts
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        bottomNavView = findViewById(R.id.bottomNav)
        bottomNavView.setOnItemSelectedListener(::bottomNavItemSelected)

        homeFragment = HomeFragment()
        notificationFragment = NotificationFragment()
        myOrdersFragment = MyOrdersFragment()
        accountFragment = AccountFragment()
        categoriesFragment = CategoriesFragment()

        showHomeFragment()
    }

    private fun bottomNavItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuHome -> showHomeFragment()
            R.id.menuNotification -> showNotificationFragment()
            R.id.menuMyOrders -> showMyOrdersFragment()
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

    private fun showMyOrdersFragment() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, myOrdersFragment)
            commit()
        }
    }

    private fun showAccountFragment() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, accountFragment)
            commit()
        }
    }

    private fun showCategoriesFragment() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, categoriesFragment)
            commit()
        }
    }

}