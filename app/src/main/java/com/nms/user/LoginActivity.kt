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


class LoginActivity : AppCompatActivity() {
    private lateinit var mobileNo: EditText
    private lateinit var password: EditText

    private lateinit var btnLogin: AppCompatButton
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Init Views
        init()

        // check if user is already logged in
        if (Authentication.isLoggedIn(this)) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        // Set on click listener on Login button
        btnLogin.setOnClickListener {
            doLogin()
        }

        // Set on click listener on Register button
        findViewById<TextView>(R.id.txtregister).setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

    }

    // Function to init views
    private fun init(){
        mobileNo = findViewById(R.id.txtMobileNumber)
        password = findViewById(R.id.txtPassword)
        btnLogin = findViewById(R.id.btnLogin)
    }

    //  Function to validate Inputs
    private fun validateInput() : Boolean{
        return when{
            mobileNo.text.isEmpty() ->{
                mobileNo.error = "Please Enter Mobile No"
                false
            }
            password.text.isEmpty() ->{
                password.error = "Please Enter Password"
                false
            }
            else ->{
                true
            }
        }
    }

    // Function to Do Login
    private fun doLogin(){
        if(!validateInput())
            return

        val mobileNo = mobileNo.text.toString()
        val password = password.text.toString()

        CoroutineScope(Dispatchers.IO).launch {
            val response = AuthRepository.loginUser(UserModel(phone = mobileNo, password = password), this@LoginActivity)
            if(response.code == 200){
                Authentication.storeToken(this@LoginActivity, response.data.toString())

                // redirect to Home Fragment
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            }else{
                // show error message
                withContext(Dispatchers.Main){
                    Helper.showToast(this@LoginActivity, response.code.toString())
                }
            }
        }
    }
}

