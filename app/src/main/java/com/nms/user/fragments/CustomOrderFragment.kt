package com.nms.user.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.nms.user.R
import com.nms.user.models.CustomOrderModel
import com.nms.user.repo.CustomOrderRepository
import com.nms.user.utils.Helper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CustomOrderFragment : Fragment() {
    private lateinit var etName: EditText
    private lateinit var etPhone: EditText
    private lateinit var etEmail: EditText
    private lateinit var etAddress: EditText
    private lateinit var etCity: EditText
    private lateinit var etState: EditText
    private lateinit var etZip: EditText
    private lateinit var etProductName: EditText
    private lateinit var etBrandName: EditText
    private lateinit var etQuantity: EditText
    private lateinit var etNotes: EditText
    private lateinit var btnSubmit: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_custom_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)

        btnSubmit.setOnClickListener {
            if (validateInput()) {
                // ask user to confirm
                Helper.showConfirmationDialog(
                    requireContext(),
                    "Confirm Order",
                    "Are you sure you want to submit this custom order?",
                    "Submit",
                    "Cancel",
                    {
                        submitCustomOrder()
                    },
                    {}
                )
            }
        }
    }

    // Function to initialize Views
    private fun initViews(view: View) {
        etName = view.findViewById(R.id.etName)
        etPhone = view.findViewById(R.id.etPhone)
        etEmail = view.findViewById(R.id.etEmail)
        etAddress = view.findViewById(R.id.etAddress)
        etCity = view.findViewById(R.id.etCity)
        etState = view.findViewById(R.id.etState)
        etZip = view.findViewById(R.id.etZip)
        etProductName = view.findViewById(R.id.etProductName)
        etBrandName = view.findViewById(R.id.etBrandName)
        etQuantity = view.findViewById(R.id.etQuantity)
        etNotes = view.findViewById(R.id.etNotes)
        btnSubmit = view.findViewById(R.id.btnSubmit)

        // Set default values for testing
        etName.setText("Test User")
        etPhone.setText("1234567890")
        etEmail.setText("test@mail.com")
        etAddress.setText("Test Address")
        etCity.setText("Test City")
        etState.setText("Test State")
        etZip.setText("123456")
        etProductName.setText("Test Product")
        etBrandName.setText("Test Brand")
        etQuantity.setText("1")
        etNotes.setText("Test Notes")
    }

    // Function to validate input
    private fun validateInput(): Boolean {
        return when {
            etName.text.toString().trim().isEmpty() -> {
                etName.error = "Name is required"
                false
            }
            etPhone.text.toString().trim().isEmpty() -> {
                etPhone.error = "Phone is required"
                false
            }
            etEmail.text.toString().trim().isEmpty() -> {
                etEmail.error = "Email is required"
                false
            }
            etAddress.text.toString().trim().isEmpty() -> {
                etAddress.error = "Address is required"
                false
            }
            etCity.text.toString().trim().isEmpty() -> {
                etCity.error = "City is required"
                false
            }
            etState.text.toString().trim().isEmpty() -> {
                etState.error = "State is required"
                false
            }
            etZip.text.toString().trim().isEmpty() -> {
                etZip.error = "Zip is required"
                false
            }
            etProductName.text.toString().trim().isEmpty() -> {
                etProductName.error = "Product Name is required"
                false
            }
            etBrandName.text.toString().trim().isEmpty() -> {
                etBrandName.error = "Brand Name is required"
                false
            }
            etQuantity.text.toString().trim().isEmpty() -> {
                etQuantity.error = "Quantity is required"
                false
            }
            else -> true
        }
    }

    // Function to submit Custom Order
    private fun submitCustomOrder() {
        val customOrderModel = CustomOrderModel(
            name = etName.text.toString().trim(),
            phone = etPhone.text.toString().trim(),
            email = etEmail.text.toString().trim(),
            address = etAddress.text.toString().trim(),
            city = etCity.text.toString().trim(),
            state = etState.text.toString().trim(),
            pincode = etZip.text.toString().trim(),
            product_name = etProductName.text.toString().trim(),
            brand_name = etBrandName.text.toString().trim(),
            quantity = etQuantity.text.toString().trim(),
            notes = etNotes.text.toString().trim(),
        )
        CoroutineScope(Dispatchers.IO).launch {
            val response = CustomOrderRepository.addCustomOrder(requireContext(), customOrderModel)
            if (response.code == 201) {
                requireActivity().runOnUiThread {
                    clearInputFields()
                    // Show success message
                    Helper.showConfirmationDialog(
                        requireContext(),
                        "Success",
                        "Your custom order has been submitted successfully. We will contact you soon.",
                        "OK",
                        "",
                        {},
                        {})
                }
            } else {
                requireActivity().runOnUiThread {
                    // Show error message
                    Helper.showConfirmationDialog(
                        requireContext(),
                        "Error",
                        response.code.toString(),
                        "OK",
                        "",
                        {},
                        {})
                }
            }
        }
    }

    // function to clear input fields
    private fun clearInputFields() {
        etName.text.clear()
        etPhone.text.clear()
        etEmail.text.clear()
        etAddress.text.clear()
        etCity.text.clear()
        etState.text.clear()
        etZip.text.clear()
        etProductName.text.clear()
        etBrandName.text.clear()
        etQuantity.text.clear()
        etNotes.text.clear()
    }
}