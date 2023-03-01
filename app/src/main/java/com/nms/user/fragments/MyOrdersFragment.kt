package com.nms.user.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.nms.user.R

class MyOrdersFragment : Fragment() {

    private lateinit var txtEmptyOrder: TextView
    private lateinit var rvMyOrders: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_orders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // initialize views
        initializeViews()

    }

    // Function to initialize views
    private fun initializeViews() {
        txtEmptyOrder = view?.findViewById(R.id.txtEmptyOrder)!!
        rvMyOrders = view?.findViewById(R.id.rvMyOrders)!!
    }
}