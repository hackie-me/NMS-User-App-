package com.nms.user

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.nms.user.adapters.CartAdapter
import com.nms.user.adapters.ProductsAdapter
import com.nms.user.models.CartModel
import com.nms.user.models.ProductModel
import com.nms.user.repo.CartRepository
import com.nms.user.repo.ProductRepository
import com.nms.user.utils.Helper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartActivity : AppCompatActivity()
{
    private lateinit var emptyCartLayout: LinearLayout
    private lateinit var cartLayout: LinearLayout
    private lateinit var btnShopNow: AppCompatButton
    private lateinit var btnCheckout: AppCompatButton
    private lateinit var rvCartItem: RecyclerView
    private lateinit var txtOrderTotal: TextView
    private lateinit var txtItemsDiscount: TextView
    private lateinit var txtShippingPrice: TextView
    private lateinit var txtTotalCartValue: TextView
    private lateinit var imgBackArrow: ImageView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        // Initialize
        initViews()

        // Set click listeners
        btnShopNow.setOnClickListener {
            // TODO: Navigate to Main Activity
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        imgBackArrow.setOnClickListener {
            // TODO: Navigate to Main Activity
            finish()
        }

        btnCheckout.setOnClickListener {
            // TODO: Navigate to Checkout Activity
            startActivity(Intent(this, CheckoutActivity::class.java))
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        // Check if cart is empty
        if (CartRepository.getCartCount(this) == 0)
        {
            emptyCartLayout.visibility = View.VISIBLE
            cartLayout.visibility = View.GONE
        } else
        {
            emptyCartLayout.visibility = View.GONE
            cartLayout.visibility = View.VISIBLE
        }


    }

    // Function to get the product details from server and set the data to views
    private fun getProductDetails(productId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = ProductRepository.getProductById(productId)
            val products = Gson().fromJson(response.data, ProductModel::class.java)
            withContext(Dispatchers.Main) {
                if (response.code == 200) {
                    val cartModel: Array<CartModel>? = null
                    cartModel?.let {
                        val cartAdapter = CartAdapter(this@CartActivity, it)
                        rvCartItem.layoutManager = GridLayoutManager(this@CartActivity, 1)
                        rvCartItem.adapter = cartAdapter
                    }
                } else {
                    Helper.showSnackBar(
                        findViewById(android.R.id.content),
                        "Error: ${response.message}"
                    )
                }
            }
        }
    }

    // Function to initialize views
    private fun initViews() {
        emptyCartLayout = findViewById(R.id.emptyCartLayout)
        cartLayout = findViewById(R.id.cartLayout)
        btnShopNow = findViewById(R.id.btnShopNow)
        btnCheckout = findViewById(R.id.btnCheckout)
        rvCartItem = findViewById(R.id.rvCartItem)
        txtOrderTotal = findViewById(R.id.txtOrderTotal)
        txtItemsDiscount = findViewById(R.id.txtItemsDiscount)
        txtShippingPrice = findViewById(R.id.txtShippingPrice)
        txtTotalCartValue = findViewById(R.id.txtTotalCartValue)
        imgBackArrow = findViewById(R.id.imgBackArrow)
    }


}