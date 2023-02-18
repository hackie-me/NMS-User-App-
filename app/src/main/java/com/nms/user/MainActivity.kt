package com.nms.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nms.user.fragments.*
import com.nms.user.utils.Helper

class MainActivity : AppCompatActivity()
{
    private lateinit var bottomNavView: BottomNavigationView

    private lateinit var homeFragment: HomeFragment
    private lateinit var notificationFragment: NotificationFragment
    private lateinit var myOrdersFragment: MyOrdersFragment
    private lateinit var accountFragment: AccountFragment
    private lateinit var categoriesFragment: CategoriesFragment

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Hide Focus on EditText when activity starts
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        bottomNavView = findViewById(R.id.bottomNav)
        bottomNavView.setOnItemSelectedListener(::bottomNavItemSelected)

        homeFragment = HomeFragment()
        notificationFragment = NotificationFragment()
        myOrdersFragment = MyOrdersFragment()
        accountFragment = AccountFragment()
        categoriesFragment = CategoriesFragment()

        showHomeFragment()
    }

    private fun bottomNavItemSelected(item: MenuItem): Boolean
    {
        when (item.itemId)
        {
            R.id.menuHome -> showHomeFragment()
            R.id.menuNotification -> showNotificationFragment()
            R.id.menuMyOrders -> showMyOrdersFragment()
            R.id.menuAccount -> showAccountFragment()
            R.id.menuCategories -> showCategoriesFragment()
            else -> return false
        }

        return true
    }

    private fun showHomeFragment()
    {
        val manager = supportFragmentManager.beginTransaction()
        manager.replace(R.id.fragmentContainer, homeFragment)
        manager.commit()
    }

    private fun showNotificationFragment()
    {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, notificationFragment)
            commit()
        }
    }

    private fun showMyOrdersFragment ()
    {
        val manager = supportFragmentManager.beginTransaction()
        manager.replace(R.id.fragmentContainer, myOrdersFragment)
        manager.commit()
    }

    private fun showAccountFragment()
    {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, accountFragment )
            commit()
        }
    }

    private fun showCategoriesFragment()
    {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, categoriesFragment )
            commit()
        }
    }

}