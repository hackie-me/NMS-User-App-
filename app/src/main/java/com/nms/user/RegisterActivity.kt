package com.nms.user

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.nms.user.models.UserModel
import com.nms.user.repo.AuthRepository
import com.nms.user.service.Authentication
import com.nms.user.utils.Helper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {

    lateinit var txtFullName: EditText
    lateinit var txtMobileNumber: EditText
    lateinit var txtPassword: EditText
    lateinit var txtConfirmPassword: EditText
    lateinit var btnRegister: AppCompatButton
    lateinit var tvLogin: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // check if user is already logged in
        if (Authentication.isLoggedIn(this)) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        // Initialize views
        initViews()

        // Set on click listener on register button
        btnRegister.setOnClickListener {
            registerUser()
        }

        findViewById<TextView>(R.id.txtLogin).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    // Function to initialize views
    private fun initViews() {
        txtFullName = findViewById(R.id.txtFullName)
        txtMobileNumber = findViewById(R.id.txtMobileNumber)
        txtPassword = findViewById(R.id.txtPassword)
        txtConfirmPassword = findViewById(R.id.txtConfirmPassword)
        btnRegister = findViewById(R.id.btnRegister)
        tvLogin = findViewById(R.id.txtLogin)
    }

    // Function to validate user input
    private fun validateInput(): Boolean {
        return when {
            txtFullName.text.toString().isEmpty() -> {
                txtFullName.error = "Full Name is required"
                false
            }
            txtMobileNumber.text.toString().isEmpty() -> {
                txtMobileNumber.error = "Mobile Number is required"
                false
            }

            // Check if mobile number is valid or not
            !validateMobileNumber() -> false

            txtPassword.text.toString().isEmpty() -> {
                txtPassword.error = "Password is required"
                false
            }
            txtConfirmPassword.text.toString().isEmpty() -> {
                txtConfirmPassword.error = "Confirm Password is required"
                false
            }
            txtPassword.text.toString() != txtConfirmPassword.text.toString() -> {
                txtConfirmPassword.error = "Password and Confirm Password do not match"
                false
            }
            else -> true
        }
    }

    // Function to validate mobile number
    private fun validateMobileNumber(): Boolean {
        return when {
            txtMobileNumber.text.toString().length != 10 -> {
                txtMobileNumber.error = "Mobile Number is not valid"
                false
            }
            else -> true
        }
    }

    // Function to register user
    private fun registerUser() {
        if (!validateInput()) {
            // If input is not valid, return
            Helper.showToast(this, "Please enter valid input")
            return
        }
        val fullName = txtFullName.text.toString()
        val mobileNumber = txtMobileNumber.text.toString()
        val password = txtPassword.text.toString()
        val confirmPassword = txtConfirmPassword.text.toString()
        // User Model
        val user = UserModel(
            full_name = fullName,
            phone = mobileNumber,
            password = password,
            confirm_password = confirmPassword
        )

        // Coroutine to register user
        CoroutineScope(Dispatchers.IO).launch {
            val response = AuthRepository.registerUser(user)
            withContext(Dispatchers.Main) {
                if (response.code == 201) {
                    Helper.showToast(this@RegisterActivity, "User registered successfully")

                    // Store Authentication Token
                    response.data?.let { Authentication.storeToken(this@RegisterActivity, it) }

                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    finish()
                } else {
                    response.message?.let { Helper.showToast(this@RegisterActivity, it) }
                    response.data?.let { Helper.showToast(this@RegisterActivity, it) }
                }
            }
        }
    }
}
