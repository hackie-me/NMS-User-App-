package com.nms.user.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.nms.user.R
import com.nms.user.models.AddressModel
import com.nms.user.utils.Helper

class AddressAdapter(
    private val context: Context,
    private val address: Array<AddressModel>
) : RecyclerView.Adapter<AddressAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_address, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemViewModel = address[position]

        // set the data to the views
        val address =
            "${itemViewModel.address}, ${itemViewModel.city}, ${itemViewModel.state}, ${itemViewModel.pincode}"
        holder.address.text = address
    }

    override fun getItemCount(): Int = address.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val address: TextView

        init {
            address = itemView.findViewById(R.id.tvAddress)
            // set the click listener
            itemView.findViewById<MaterialCardView>(R.id.cardViewAddress).setOnClickListener{
                itemView.findViewById<MaterialCardView>(R.id.cardViewAddress).isChecked = !itemView.findViewById<MaterialCardView>(R.id.cardViewAddress).isChecked
                // Helper.showToast(itemView.context, address.text.toString())
                Helper.storeSharedPreference(itemView.context, "deliveryAddress", address.text.toString())
            }
        }
    }
}