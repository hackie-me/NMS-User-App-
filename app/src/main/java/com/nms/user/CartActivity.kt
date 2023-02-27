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
import com.nms.user.models.CartModel
import com.nms.user.models.CartProductPrice
import com.nms.user.models.ProductModel
import com.nms.user.repo.CartRepository
import com.nms.user.repo.ProductRepository
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
    private lateinit var cartListArray: ArrayList<CartModel>
    private lateinit var cartProductPriceListArray: ArrayList<CartProductPrice>
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

    override fun onResume() {
        super.onResume()
        // Check if cart is empty
        if (CartRepository.getCartCount(this) == 0) {
            emptyCartLayout.visibility = View.VISIBLE
            cartLayout.visibility = View.GONE
        } else {
            emptyCartLayout.visibility = View.GONE
            cartLayout.visibility = View.VISIBLE
        }
    }

    // Function to get the product details from server and set the data to views
    private fun getCartItems() {
        val cartItems = CartRepository.getAllProductsFromCart(this)
        // Get the product details for each product in cart
        for (i in 0 until cartItems.size) {
            CoroutineScope(Dispatchers.IO).launch {
                val response = ProductRepository.getProductById(cartItems[i])
                if (response.code == 200) {
                    val product = Gson().fromJson(response.data, ProductModel::class.java)
                    withContext(Dispatchers.Main) {
                        cartListArray.add(
                            CartModel(
                                id = product.id,
                                productName = product.name,
                                productDescription = product.description,
                                productImage = product.image,
                                productPrice = product.price.toInt(),
                                productDiscount = product.discount
                            )
                        )
                        cartProductPriceListArray.add(
                            CartProductPrice(
                                productId = product.id,
                                price = product.price.toInt()
                            )
                        )
                        // Set the data to views
                        rvCartItem.layoutManager = GridLayoutManager(this@CartActivity, 1)
                        rvCartItem.adapter =
                            CartAdapter(this@CartActivity, cartListArray, this@CartActivity)

                        // update the cart total
                        updateCart()
                    }
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
        cartListArray = ArrayList()
        cartProductPriceListArray = ArrayList()
    }

    override fun onMinusClick(position: Int) {
        // Getting Clicked Items Product ID
        val productId = cartListArray[position].id
        // Getting Clicked Items Quantity and Total Price and Price
        val quantity = cartListArray[position].productQuantity
        val price = cartListArray[position].productPrice
        val defaultPrice = cartProductPriceListArray[position].price

        // Checking if quantity is greater than 1
        if (quantity > 1) {
            // Update product Price
            val productPrice = price - defaultPrice

            // Updating the quantity in cart
            if (CartRepository.updateProductQuantity(this, productId, quantity - 1, productPrice)) {
                // Updating the quantity in cart list array
                cartListArray[position].productQuantity = quantity - 1
                cartListArray[position].productPrice = productPrice
                // Updating the quantity in cart adapter
                rvCartItem.adapter?.notifyItemChanged(position)

                // Update the cart
                updateCart()
            }
        }
    }

    override fun onPlusClick(position: Int) {
        // Getting Clicked Items Product ID
        val productId = cartListArray[position].id
        // Getting Clicked Items Quantity and Total Price and Price
        val quantity = cartListArray[position].productQuantity
        val price = cartListArray[position].productPrice
        val defaultPrice = cartProductPriceListArray[position].price

        // Update product Price
        val productPrice = price + defaultPrice

        // Updating the quantity, total price and price in cart
        if (CartRepository.updateProductQuantity(this, productId, quantity + 1, productPrice)) {
            // Updating the quantity in cart list array
            cartListArray[position].productQuantity = quantity + 1
            cartListArray[position].productPrice = productPrice
            // Updating the quantity in cart adapter
            rvCartItem.adapter?.notifyItemChanged(position)

            // Update the cart
            updateCart()
        }
    }

    override fun onRemoveClick(position: Int) {
        // Getting Clicked Items Product ID
        val productId = cartListArray[position].id
        // Removing the product from cart
        CartRepository.removeFromCart(this, productId)
        // Removing the product from cart list array
        cartListArray.removeAt(position)
        // Removing the product from cart adapter
        rvCartItem.adapter?.notifyItemRemoved(position)

        // Update the cart
        updateCart()
    }

    // Function to update the cart
    private fun updateCart() {
        // Calculate the total price
        orderTotal = 0
        for (j in 0 until cartListArray.size) {
            orderTotal += cartListArray[j].productPrice
        }
        txtOrderTotal.text = "₹ $orderTotal"
        // Calculate the discount
        itemsDiscount = (orderTotal * discount) / 100
        txtItemsDiscount.text = "₹ $itemsDiscount"
        // Calculate the shipping price
        // val shippingPrice = 50
        // txtShippingPrice.text = "₹ $shippingPrice"
        // Calculate the total cart value
        val totalCartValue = orderTotal - itemsDiscount
        txtTotalCartValue.text = "₹ $totalCartValue"
    }

}