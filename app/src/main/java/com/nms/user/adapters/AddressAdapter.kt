package com.nms.user.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.nms.user.R
import com.nms.user.models.AddressModel
import com.nms.user.utils.Helper

class AddressAdapter(
    private val context: Context,
    private val address: Array<AddressModel>
) : RecyclerView.Adapter<AddressAdapter.ViewHolder>() {

    private var selectedCardView: MaterialCardView? = null

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
        holder.addressId.text = itemViewModel.id

    }

    override fun getItemCount(): Int = address.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val address: TextView = itemView.findViewById(R.id.tvAddress)
        val addressId: TextView = itemView.findViewById(R.id.tvAddressId)

        init {
            // set the click listener to the card view
            itemView.findViewById<MaterialCardView>(R.id.cardViewAddress).setOnClickListener {
                val cardView = itemView.findViewById<MaterialCardView>(R.id.cardViewAddress)
                if (cardView != selectedCardView) {
                    // deselect the currently selected card view
                    selectedCardView?.isChecked = false

                    // select the clicked card view
                    cardView.isChecked = true
                    selectedCardView = cardView

                    // save the selected address to shared preferences
                    Helper.storeSharedPreference(
                        itemView.context,
                        "deliveryAddress",
                        addressId.text.toString()
                    )
                }
            }
        }
    }
}
