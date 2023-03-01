package com.nms.user

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.nms.user.adapters.AddressAdapter
import com.nms.user.models.AddressModel
import com.nms.user.models.MyOrderModel
import com.nms.user.repo.AddressRepository
import com.nms.user.repo.OrderRepository
import com.nms.user.utils.Helper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CheckoutActivity : AppCompatActivity() {
    private lateinit var btnOrderNow: AppCompatButton
    private lateinit var txtOrderTotal: TextView
    private lateinit var txtItemsDiscount: TextView
    private lateinit var txtShippingPrice: TextView
    private lateinit var txtTotalCartValue: TextView
    private lateinit var imgBackArrow: ImageView
    private lateinit var txtButtonAddMoreToCart: LinearLayout
    private lateinit var txtTotalCartCount: TextView

    // Address
    private lateinit var rvAddressList: RecyclerView
    private lateinit var txtButtonAddNewAddress: TextView
    private lateinit var linearEmptyAddress: LinearLayout
    private var addressId: String = ""

    // Special Order Notes
    private lateinit var txtSpecialOrderNotes: TextView


    // Intent Data
    private var orderTotal: Int = 0
    private var itemsDiscount: Int = 0
    private var shippingPrice: Int = 0
    private var totalCartValue: Int = 0
    private var totalCartCount: Int = 0
    private var pid = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        // Hide Focus on EditText when activity starts
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        // Initialize
        initViews()

        // Get Address List
        getAddressList()

        // Get intent data
        orderTotal = intent.getIntExtra("orderTotal", 0)
        itemsDiscount = intent.getIntExtra("itemsDiscount", 0)
        shippingPrice = intent.getIntExtra("shippingPrice", 0)
        totalCartValue = intent.getIntExtra("totalCartValue", 0)
        totalCartCount = intent.getIntExtra("totalCartCount", 0)
        pid = intent.getStringExtra("pid").toString()

        // Set data
        txtOrderTotal.text = "₹ $orderTotal"
        txtItemsDiscount.text = "₹ $itemsDiscount"
        txtShippingPrice.text = "₹ $shippingPrice"
        txtTotalCartValue.text = "₹ $totalCartValue"
        txtTotalCartCount.text = "$totalCartCount Items in your Cart"


        // Order Now Button Click
        btnOrderNow.setOnClickListener {
            Helper.showConfirmationDialog(
                this,
                "Place Order Confirmation",
                "Are you sure you want to place this order",
                "yes",
                "No",
                { doOrder() },
                {})
        }

        // Add More to Cart Button Click
        txtButtonAddMoreToCart.setOnClickListener {
            // Open Main Activity
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        // Back Arrow Click
        imgBackArrow.setOnClickListener {
            finish()
        }

        // Add New Address Button Click
        txtButtonAddNewAddress.setOnClickListener {
            // Open Add Address Activity
            startActivity(Intent(this, AddNewAddressActivity::class.java))
        }

    }

    // Function to initialize views
    private fun initViews() {
        btnOrderNow = findViewById(R.id.btnOrderNow)
        txtOrderTotal = findViewById(R.id.txtOrderTotal)
        txtItemsDiscount = findViewById(R.id.txtItemsDiscount)
        txtShippingPrice = findViewById(R.id.txtShippingPrice)
        txtTotalCartValue = findViewById(R.id.txtTotalCartValue)
        imgBackArrow = findViewById(R.id.imgBackArrow)
        txtButtonAddMoreToCart = findViewById(R.id.txtButtonAddMoreToCart)
        txtTotalCartCount = findViewById(R.id.txtTotalCartCount)
        rvAddressList = findViewById(R.id.rvAddressList)
        txtButtonAddNewAddress = findViewById(R.id.txtButtonAddNewAddress)
        linearEmptyAddress = findViewById(R.id.linearEmptyAddress)
        txtSpecialOrderNotes = findViewById(R.id.txtSpecialOrderNotes)
    }

    // Function to do order
    private fun doOrder() {
        // Check Address is selected or not
        if (Helper.fetchSharedPreference(this, "deliveryAddress").isNullOrEmpty()) {
            Helper.showToast(this, "Please select delivery address")
            return
        }
        // Get Special Order Notes
        val specialOrderNotes = txtSpecialOrderNotes.text.toString()

        // Create Order Model
        val myOrderModel = MyOrderModel(
            address = Helper.fetchSharedPreference(this, "deliveryAddress"),
            note = specialOrderNotes,
            total = totalCartValue.toString(),
        )
        // Place Order
        CoroutineScope(Dispatchers.IO).launch {
            val response = OrderRepository.placeOrder(this@CheckoutActivity, myOrderModel)
            withContext(Dispatchers.Main) {
                if (response.code == 201) {
                    // Open Order Placed Activity
                    startActivity(Intent(this@CheckoutActivity, OrderPlacedActivity::class.java))
                    finish()
                } else {
                    Helper.showToast(this@CheckoutActivity, response.code.toString())
                }
            }
        }
    }

    // Function to get address list
    private fun getAddressList() {
        CoroutineScope(Dispatchers.IO).launch {
            // Get Address List
            val response = AddressRepository.getAllAddress(this@CheckoutActivity)
            val addressList = Gson().fromJson(response.data, Array<AddressModel>::class.java)
            // if address list is Empty then show default address
            if (addressList.isEmpty()) {
                withContext(Dispatchers.Main) {
                    linearEmptyAddress.visibility = LinearLayout.VISIBLE
                }
            } else {
                withContext(Dispatchers.Main) {
                    linearEmptyAddress.visibility = LinearLayout.GONE
                    rvAddressList.visibility = RecyclerView.VISIBLE
                }
            }

            if (response.code == 200) {
                // Set Address List
                val addressAdapter = AddressAdapter(this@CheckoutActivity, addressList)
                withContext(Dispatchers.Main) {
                    rvAddressList.adapter = addressAdapter
                    rvAddressList.layoutManager = LinearLayoutManager(
                        this@CheckoutActivity, LinearLayoutManager.VERTICAL, false
                    )
                }
            } else {
                // Show Error Message
                withContext(Dispatchers.Main) {
                    Helper.showToast(this@CheckoutActivity, "Error: ${response.message}")
                }
            }
        }
    }
}