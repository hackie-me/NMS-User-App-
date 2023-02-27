package com.nms.user.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.nms.user.LoginActivity
import com.nms.user.R
import com.nms.user.service.Authentication
import com.nms.user.utils.Helper

class AccountFragment : Fragment() {

    private lateinit var linearLayoutGroupItemMyAccount : LinearLayout
    private lateinit var linearLayoutGroupItemMyOrder : LinearLayout
    private lateinit var linearLayoutGroupItemBilling : LinearLayout
    private lateinit var linearLayoutGroupItemFaq: LinearLayout
    private lateinit var linearLayoutGroupItemLogout: LinearLayout
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        // initialize views
        initializeViews()

        // initialize user data
        initializeUserData()

        // set click listener
        setClickListener()
    }

    // Function to initialize views
    private fun initializeViews(){
        linearLayoutGroupItemMyAccount = view?.findViewById(R.id.linearRowgroupitemPrivateAccount)!!
        linearLayoutGroupItemMyOrder = view?.findViewById(R.id.linearRowgroupitemMyOrders)!!
        linearLayoutGroupItemBilling = view?.findViewById(R.id.linearRowgroupitemBilling)!!
        linearLayoutGroupItemFaq = view?.findViewById(R.id.linearRowgroupitemFaq)!!
        linearLayoutGroupItemLogout = view?.findViewById(R.id.linearRowgroupitemLogout)!!
    }
    private fun initializeUserData(){
        val userFullName = let { Authentication.getDataFromToken(requireContext(), "full_name") }
        view?.findViewById<TextView>(R.id.txtUserName)?.text = "Hi, $userFullName"
    }

    // Function to set click listener for Linear layouts
    private fun setClickListener(){

        // My Account
        linearLayoutGroupItemMyAccount.setOnClickListener {
            Helper.showToast(requireContext(), "My Account")
        }

        // My Order
        linearLayoutGroupItemMyOrder.setOnClickListener {
            Helper.showToast(requireContext(), "My Order")
        }

        // Billing
        linearLayoutGroupItemBilling.setOnClickListener {
            Helper.showToast(requireContext(), "Billing")
        }

        // FAQ
        linearLayoutGroupItemFaq.setOnClickListener {
            Helper.showToast(requireContext(), "FAQ")
        }

        // Logout
        linearLayoutGroupItemLogout.setOnClickListener {
            Authentication.clearToken(requireContext())
            Helper.showToast(requireContext(), "Successfully logged out")
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }
    }
}