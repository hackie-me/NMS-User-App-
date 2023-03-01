package com.nms.user

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.nms.user.adapters.CartAdapter
import com.nms.user.models.CartModel
import com.nms.user.repo.CartRepository
import com.nms.user.utils.Helper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartActivity : AppCompatActivity(), CartAdapter.OnClickListener {
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
    private lateinit var cartListArray: Array<CartModel>
    private var orderTotal: Int = 0
    private var itemsDiscount: Int = 0
    private var discount = 5

    override fun onCreate(savedInstanceState: Bundle?) {
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
            val intent = Intent(this, CheckoutActivity::class.java)
            intent.putExtra("orderTotal", orderTotal)
            intent.putExtra("itemsDiscount", itemsDiscount)
            intent.putExtra("shippingPrice", 0)
            intent.putExtra("totalCartValue", (orderTotal - itemsDiscount))
            intent.putExtra("totalCartCount", cartListArray.size)
            startActivity(intent)
            finish()
        }
        // Get the cart items
        getCartItems()
    }

    private fun checkCartEmpty() {
        if (cartListArray.isEmpty()) {
            emptyCartLayout.visibility = View.VISIBLE
            cartLayout.visibility = View.GONE
        } else {
            emptyCartLayout.visibility = View.GONE
            cartLayout.visibility = View.VISIBLE
        }
    }

    // Function to get the product details from server and set the data to views
    private fun getCartItems() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = CartRepository.getCartItems(this@CartActivity)
            if (response.code == 200) {
                cartListArray = Gson().fromJson(response.data, Array<CartModel>::class.java)
                withContext(Dispatchers.Main) {
                    // Visibility of views
                    checkCartEmpty()

                    // layout manager
                    rvCartItem.layoutManager = LinearLayoutManager(this@CartActivity)
                    // Set the cart items to recycler view
                    rvCartItem.adapter =
                        CartAdapter(this@CartActivity, cartListArray, this@CartActivity)
                    // Calculate the total price
                    orderTotal = 0
                    itemsDiscount = 0
                    for (cartItem in cartListArray) {
                        orderTotal += (cartItem.price.toInt() * cartItem.quantity.toInt())
                        itemsDiscount += ((cartItem.price.toInt() * cartItem.quantity.toInt()) * discount / 100)
                    }
                    txtOrderTotal.text = "₹ $orderTotal"
                    txtItemsDiscount.text = "₹ $itemsDiscount"
                    val totalCartValue = orderTotal - itemsDiscount
                    txtTotalCartValue.text = "₹ $totalCartValue"
                }
            } else {
                withContext(Dispatchers.Main) {
                    Helper.showToast(this@CartActivity, response.code.toString())
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
        cartListArray = emptyArray()
    }

    override fun onMinusClick(position: Int) {
        val cartItem = cartListArray[position]
        cartItem.operation = "sub"
        CoroutineScope(Dispatchers.IO).launch {
            val response = CartRepository.updateCartQuantity(this@CartActivity, cartItem)
            if (response.code == 204) {
                withContext(Dispatchers.Main) {
                    // Update the cart
                    updateCart()
                    getCartItems()
                    // notify the adapter
                    rvCartItem.adapter?.notifyItemChanged(position)
                }
            } else {
                withContext(Dispatchers.Main) {
                    Helper.showToast(this@CartActivity, response.code.toString())
                }
            }
        }
    }

    override fun onPlusClick(position: Int) {
        val cartItem = cartListArray[position]
        cartItem.operation = "add"
        CoroutineScope(Dispatchers.IO).launch {
            val response = CartRepository.updateCartQuantity(this@CartActivity, cartItem)
            if (response.code == 204) {
                withContext(Dispatchers.Main) {
                    // Update the cart
                    updateCart()
                    getCartItems()

                    // notify the adapter
                    rvCartItem.adapter?.notifyItemChanged(position)
                }
            } else {
                withContext(Dispatchers.Main) {
                    Helper.showToast(this@CartActivity, response.code.toString())
                }
            }
        }

    }

    override fun onRemoveClick(position: Int) {
        val cartItem = cartListArray[position]
        CoroutineScope(Dispatchers.IO).launch {
            val response = CartRepository.deleteCartItem(this@CartActivity, cartItem)
            if (response.code == 200) {
                withContext(Dispatchers.Main) {
                    // Update the cart
                    cartListArray =
                        cartListArray.filterIndexed { index, _ -> index != position }.toTypedArray()
                    rvCartItem.adapter?.notifyItemRemoved(position)
                    rvCartItem.adapter?.notifyItemRangeChanged(position, cartListArray.size)
                    checkCartEmpty()
                    updateCart()
                    getCartItems()
                    // Notify adapter to update the cart
                    rvCartItem.adapter?.notifyItemChanged(position)
                }
            } else {
                withContext(Dispatchers.Main) {
                    Helper.showToast(this@CartActivity, response.code.toString())
                }
            }
        }

    }

    // Function to update the cart
    private fun updateCart() {
        // Calculate the total price
        orderTotal = 0
        for (cartItem in cartListArray) {
            orderTotal += (cartItem.price.toInt() * cartItem.quantity.toInt())
        }
        txtOrderTotal.text = "₹ $orderTotal"
        // Calculate the discount
        itemsDiscount = (orderTotal * discount) / 100
        txtItemsDiscount.text = "₹ $itemsDiscount"
        // Calculate the total cart value
        val totalCartValue = orderTotal - itemsDiscount
        txtTotalCartValue.text = "₹ $totalCartValue"
    }
}