package com.nms.user

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.nms.user.models.AddressModel
import com.nms.user.repo.AddressRepository
import com.nms.user.utils.Helper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddNewAddressActivity : AppCompatActivity() {
    private lateinit var tvAddress: TextView
    private lateinit var tvCity: TextView
    private lateinit var tvState: TextView
    private lateinit var tvPincode: TextView
    private lateinit var btnAddAddress: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_address)

        // Initialize the views
        initViews()

        // Set on click listener on the button
        btnAddAddress.setOnClickListener {
            // Validate the fields
            if (!validateFields()) {
                return@setOnClickListener
            }

            // Get the values from the fields
            val address = tvAddress.text.toString()
            val city = tvCity.text.toString()
            val state = tvState.text.toString()
            val pincode = tvPincode.text.toString()

            // Create a new address object
            val newAddress =
                AddressModel(address = address, city = city, state = state, pincode = pincode)

            CoroutineScope(Dispatchers.IO).launch {
                // Add the address to the database
                val response = AddressRepository.addAddress(this@AddNewAddressActivity, newAddress)
                if (response.code == 201) {
                    withContext(Dispatchers.Main) {
                        Helper.showToast(this@AddNewAddressActivity, "Address added successfully")
                        finish()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Helper.showToast(
                            this@AddNewAddressActivity,
                            "Error adding address : ${response.data}"
                        )
                    }
                }
            }
        }
    }

    // Function to initialize the views
    private fun initViews() {
        tvAddress = findViewById(R.id.tvAddress)
        tvCity = findViewById(R.id.tvCity)
        tvState = findViewById(R.id.tvState)
        tvPincode = findViewById(R.id.tvPincode)
        btnAddAddress = findViewById(R.id.btnAddAddress)
    }

    // Function to validate the fields
    private fun validateFields(): Boolean {
        if (tvAddress.text.toString().isEmpty()) {
            tvAddress.error = "Please enter address"
            return false
        }
        if (tvCity.text.toString().isEmpty()) {
            tvCity.error = "Please enter city"
            return false
        }
        if (tvState.text.toString().isEmpty()) {
            tvState.error = "Please enter state"
            return false
        }
        if (tvPincode.text.toString().isEmpty()) {
            tvPincode.error = "Please enter pincode"
            return false
        }
        return true
    }

}