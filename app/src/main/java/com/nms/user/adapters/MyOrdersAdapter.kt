package com.nms.user.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nms.user.R
import com.nms.user.models.MyOrderModel

class MyOrdersAdapter(
    var myOrdersList: List<MyOrderModel>,
) : RecyclerView.Adapter<MyOrdersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.list_item_cart, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = myOrdersList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    // View Holder Class
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}