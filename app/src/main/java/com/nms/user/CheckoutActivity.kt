package com.nms.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView

class CheckoutActivity : AppCompatActivity()
{
    private lateinit var btnOrderNow: AppCompatButton
    private lateinit var rvCartItem : RecyclerView
    private lateinit var txtOrderTotal: TextView
    private lateinit var txtItemsDiscount: TextView
    private lateinit var txtShippingPrice: TextView
    private lateinit var txtTotalCartValue: TextView
    private lateinit var imgBackArrow: ImageView
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_out)

        // Initialize
        initViews()

    }
    // Function to initialize views
    private fun initViews()
    {
        btnOrderNow = findViewById(R.id.btnOrderNow)
        rvCartItem = findViewById(R.id.rvCartItem)
        txtOrderTotal = findViewById(R.id.txtOrderTotal)
        txtItemsDiscount = findViewById(R.id.txtItemsDiscount)
        txtShippingPrice = findViewById(R.id.txtShippingPrice)
        txtTotalCartValue = findViewById(R.id.txtTotalCartValue)
        imgBackArrow = findViewById(R.id.imgBackArrow)
    }
}